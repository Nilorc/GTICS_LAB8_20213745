package com.example.lab8_20213745.repository;

import com.example.lab8_20213745.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    // Este repositorio utiliza los métodos CRUD de JpaRepository
}
