package com.example.lab8_20213745.controller;

import com.example.lab8_20213745.entity.Evento;
import com.example.lab8_20213745.entity.Reserva;
import com.example.lab8_20213745.repository.EventoRepository;
import com.example.lab8_20213745.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepositorio;

    @Autowired
    private ReservaRepository reservaRepositorio;

    // Listado de eventos con filtro por fecha
    @GetMapping
    public ResponseEntity<List<Evento>> listarEventos(@RequestParam(value = "fecha", required = false) LocalDate fecha) {
        List<Evento> eventos = (fecha != null) ?
                eventoRepositorio.findByFechaOrderByFechaAsc(fecha) :
                eventoRepositorio.findAllByOrderByFechaAsc();
        return ResponseEntity.ok(eventos);
    }

    // Crear un nuevo evento
    @PostMapping
    public ResponseEntity<HashMap<String, Object>> crearEvento(@RequestBody Evento evento) {
        HashMap<String, Object> respuesta = new HashMap<>();
        if (evento.getFecha().isBefore(LocalDate.now())) {
            respuesta.put("resultado", "error");
            respuesta.put("mensaje", "La fecha del evento debe ser en el futuro");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        evento.setReservasActuales(0); // Inicia con cero reservas
        eventoRepositorio.save(evento);
        respuesta.put("resultado", "creado");
        respuesta.put("evento", evento);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    // Reservar un lugar en un evento
    @PostMapping("/{id}/reservar")
    public ResponseEntity<HashMap<String, Object>> hacerReserva(
            @PathVariable("id") int idEvento,
            @RequestBody Reserva reserva) {
        HashMap<String, Object> respuesta = new HashMap<>();
        Optional<Evento> eventoOpt = eventoRepositorio.findById(idEvento);

        if (eventoOpt.isPresent()) {
            Evento evento = eventoOpt.get();
            int nuevasReservas = evento.getReservasActuales() + reserva.getCupos();
            if (nuevasReservas > evento.getCapacidadMaxima()) {
                respuesta.put("resultado", "error");
                respuesta.put("mensaje", "No hay suficiente capacidad para la reserva solicitada");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
            evento.setReservasActuales(nuevasReservas);
            eventoRepositorio.save(evento);
            reserva.setIdEvento(idEvento);
            reservaRepositorio.save(reserva);
            respuesta.put("resultado", "creado");
            respuesta.put("reserva", reserva);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } else {
            respuesta.put("resultado", "error");
            respuesta.put("mensaje", "Evento no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    // Eliminar una reserva
    @DeleteMapping("/reserva/{id}")
    public ResponseEntity<HashMap<String, Object>> cancelarReserva(@PathVariable("id") int idReserva) {
        HashMap<String, Object> respuesta = new HashMap<>();
        Optional<Reserva> reservaOpt = reservaRepositorio.findById(idReserva);

        if (reservaOpt.isPresent()) {
            Reserva reserva = reservaOpt.get();
            Optional<Evento> eventoOpt = eventoRepositorio.findById(reserva.getIdEvento());
            if (eventoOpt.isPresent()) {
                Evento evento = eventoOpt.get();
                evento.setReservasActuales(evento.getReservasActuales() - reserva.getCupos());
                eventoRepositorio.save(evento);
            }
            reservaRepositorio.deleteById(idReserva);
            respuesta.put("resultado", "eliminado");
            return ResponseEntity.ok(respuesta);
        } else {
            respuesta.put("resultado", "error");
            respuesta.put("mensaje", "Reserva no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }
}
