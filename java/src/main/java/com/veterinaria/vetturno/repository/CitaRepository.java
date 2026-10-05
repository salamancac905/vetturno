package com.veterinaria.vetturno.repository;

import com.veterinaria.vetturno.model.Cita;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    boolean existsByVeterinarioIdAndFechaHora(Long veterinarioId, LocalDateTime fechaHora);

    List<Cita> findByVeterinarioId(Long veterinarioId);
}
