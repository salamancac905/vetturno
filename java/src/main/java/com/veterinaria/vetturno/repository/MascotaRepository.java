package com.veterinaria.vetturno.repository;

import com.veterinaria.vetturno.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}
