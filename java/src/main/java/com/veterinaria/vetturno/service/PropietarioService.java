package com.veterinaria.vetturno.service;

import com.veterinaria.vetturno.dto.PropietarioDTO;
import com.veterinaria.vetturno.dto.PropietarioRequest;
import com.veterinaria.vetturno.model.Propietario;
import com.veterinaria.vetturno.repository.PropietarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropietarioService {

    private final PropietarioRepository propietarioRepository;

    public PropietarioService(PropietarioRepository propietarioRepository) {
        this.propietarioRepository = propietarioRepository;
    }

    @Transactional
    public PropietarioDTO crear(PropietarioRequest request) {
        Propietario propietario = new Propietario();
        propietario.setNombre(request.nombre());
        propietario.setTelefono(request.telefono());
        propietario.setEmail(request.email());
        return toDto(propietarioRepository.save(propietario));
    }

    @Transactional(readOnly = true)
    public List<PropietarioDTO> listar() {
        return propietarioRepository.findAll().stream().map(this::toDto).toList();
    }

    private PropietarioDTO toDto(Propietario propietario) {
        return new PropietarioDTO(
                propietario.getId(),
                propietario.getNombre(),
                propietario.getTelefono(),
                propietario.getEmail()
        );
    }
}
