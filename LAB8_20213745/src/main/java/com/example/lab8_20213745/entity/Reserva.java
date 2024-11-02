package com.example.lab8_20213745.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reservas")
@Getter
@Setter
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_evento", nullable = false)
    private int idEvento;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String correo;

    @Column(nullable = false)
    private int cupos;

}

