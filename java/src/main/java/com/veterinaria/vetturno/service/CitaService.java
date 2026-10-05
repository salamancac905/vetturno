package com.veterinaria.vetturno.service;

import com.veterinaria.vetturno.dto.CitaDTO;
import com.veterinaria.vetturno.dto.CitaRequest;
import com.veterinaria.vetturno.exception.BadRequestException;
import com.veterinaria.vetturno.exception.ResourceNotFoundException;
import com.veterinaria.vetturno.model.Cita;
import com.veterinaria.vetturno.model.Mascota;
import com.veterinaria.vetturno.model.Veterinario;
import com.veterinaria.vetturno.repository.CitaRepository;
import com.veterinaria.vetturno.repository.MascotaRepository;
import com.veterinaria.vetturno.repository.VeterinarioRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;

    public CitaService(
            CitaRepository citaRepository,
            MascotaRepository mascotaRepository,
            VeterinarioRepository veterinarioRepository
    ) {
        this.citaRepository = citaRepository;
        this.mascotaRepository = mascotaRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    @Transactional
    public CitaDTO crear(CitaRequest request) {
        if (request.fechaHora().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("La fecha y hora de la cita no puede ser anterior a la actual");
        }

        if (citaRepository.existsByVeterinarioIdAndFechaHora(request.veterinarioId(), request.fechaHora())) {
            throw new BadRequestException("El veterinario ya tiene una cita en ese horario");
        }

        Mascota mascota = mascotaRepository.findById(request.mascotaId())
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada"));
        Veterinario veterinario = veterinarioRepository.findById(request.veterinarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinario no encontrado"));

        Cita cita = new Cita();
        cita.setFechaHora(request.fechaHora());
        cita.setMotivo(request.motivo());
        cita.setMascota(mascota);
        cita.setVeterinario(veterinario);

        return toDto(citaRepository.save(cita));
    }

    @Transactional(readOnly = true)
    public List<CitaDTO> listar() {
        return citaRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<CitaDTO> listarPorVeterinario(Long veterinarioId) {
        if (!veterinarioRepository.existsById(veterinarioId)) {
            throw new ResourceNotFoundException("Veterinario no encontrado");
        }
        return citaRepository.findByVeterinarioId(veterinarioId).stream().map(this::toDto).toList();
    }

    private CitaDTO toDto(Cita cita) {
        return new CitaDTO(
                cita.getId(),
                cita.getFechaHora(),
                cita.getMotivo(),
                cita.getMascota().getNombre(),
                cita.getMascota().getPropietario().getNombre(),
                cita.getVeterinario().getNombre()
        );
    }
}
