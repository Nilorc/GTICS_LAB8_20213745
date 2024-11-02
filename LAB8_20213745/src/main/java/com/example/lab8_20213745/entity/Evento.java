package com.example.lab8_20213745.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "eventos")
@Getter
@Setter
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private String categoria;

    @Column(name = "capacidad_maxima", nullable = false)
    private int capacidadMaxima;

    @Column(name = "reservas_actuales", nullable = false)
    private int reservasActuales;


}
