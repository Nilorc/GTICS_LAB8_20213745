package com.example.lab8_20213745.repository;

import com.example.lab8_20213745.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {

    // Listar eventos por fecha específica y ordenar por fecha ascendente
    List<Evento> findByFechaOrderByFechaAsc(LocalDate fecha);

    // Listar todos los eventos ordenados por fecha ascendente
    List<Evento> findAllByOrderByFechaAsc();
}

