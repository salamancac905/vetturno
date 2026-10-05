package com.veterinaria.vetturno.service;

import com.veterinaria.vetturno.dto.MascotaDTO;
import com.veterinaria.vetturno.dto.MascotaRequest;
import com.veterinaria.vetturno.exception.ResourceNotFoundException;
import com.veterinaria.vetturno.model.Mascota;
import com.veterinaria.vetturno.model.Propietario;
import com.veterinaria.vetturno.repository.MascotaRepository;
import com.veterinaria.vetturno.repository.PropietarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final PropietarioRepository propietarioRepository;

    public MascotaService(MascotaRepository mascotaRepository, PropietarioRepository propietarioRepository) {
        this.mascotaRepository = mascotaRepository;
        this.propietarioRepository = propietarioRepository;
    }

    @Transactional
    public MascotaDTO crear(MascotaRequest request) {
        Propietario propietario = propietarioRepository.findById(request.propietarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Propietario no encontrado"));

        Mascota mascota = new Mascota();
        mascota.setNombre(request.nombre());
        mascota.setEspecie(request.especie());
        mascota.setRaza(request.raza());
        mascota.setPropietario(propietario);
        return toDto(mascotaRepository.save(mascota));
    }

    @Transactional(readOnly = true)
    public List<MascotaDTO> listar() {
        return mascotaRepository.findAll().stream().map(this::toDto).toList();
    }

    private MascotaDTO toDto(Mascota mascota) {
        Propietario propietario = mascota.getPropietario();
        return new MascotaDTO(
                mascota.getId(),
                mascota.getNombre(),
                mascota.getEspecie(),
                mascota.getRaza(),
                propietario.getId(),
                propietario.getNombre()
        );
    }
}
