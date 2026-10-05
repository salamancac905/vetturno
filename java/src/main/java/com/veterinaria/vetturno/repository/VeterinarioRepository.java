package com.veterinaria.vetturno.repository;

import com.veterinaria.vetturno.model.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
}
