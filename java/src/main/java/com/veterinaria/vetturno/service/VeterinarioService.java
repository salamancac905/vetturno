package com.veterinaria.vetturno.service;

import com.veterinaria.vetturno.dto.VeterinarioDTO;
import com.veterinaria.vetturno.dto.VeterinarioRequest;
import com.veterinaria.vetturno.model.Veterinario;
import com.veterinaria.vetturno.repository.VeterinarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;

    public VeterinarioService(VeterinarioRepository veterinarioRepository) {
        this.veterinarioRepository = veterinarioRepository;
    }

    @Transactional
    public VeterinarioDTO crear(VeterinarioRequest request) {
        Veterinario veterinario = new Veterinario();
        veterinario.setNombre(request.nombre());
        veterinario.setEspecialidad(request.especialidad());
        return toDto(veterinarioRepository.save(veterinario));
    }

    @Transactional(readOnly = true)
    public List<VeterinarioDTO> listar() {
        return veterinarioRepository.findAll().stream().map(this::toDto).toList();
    }

    private VeterinarioDTO toDto(Veterinario veterinario) {
        return new VeterinarioDTO(
                veterinario.getId(),
                veterinario.getNombre(),
                veterinario.getEspecialidad()
        );
    }
}
