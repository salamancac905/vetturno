package com.veterinaria.vetturno.controller;

import com.veterinaria.vetturno.dto.PropietarioDTO;
import com.veterinaria.vetturno.dto.PropietarioRequest;
import com.veterinaria.vetturno.service.PropietarioService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/propietarios")
public class PropietarioController {

    private final PropietarioService propietarioService;

    public PropietarioController(PropietarioService propietarioService) {
        this.propietarioService = propietarioService;
    }

    @PostMapping
    public ResponseEntity<PropietarioDTO> crear(@Valid @RequestBody PropietarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propietarioService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<PropietarioDTO>> listar() {
        return ResponseEntity.ok(propietarioService.listar());
    }
}
