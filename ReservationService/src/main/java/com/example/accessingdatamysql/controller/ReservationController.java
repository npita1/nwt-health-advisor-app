package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.entity.EventEntity;
import com.example.accessingdatamysql.entity.ReservationEntity;
import com.example.accessingdatamysql.exceptions.EventNotFoundException;
import com.example.accessingdatamysql.exceptions.ReservationNotFoundException;

import com.example.accessingdatamysql.repository.EventRepository;
import com.example.accessingdatamysql.repository.ReservationRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController// This means that this class is a Controller
@Validated
@RequestMapping(path="/reservation") // This means URL's start with /demo (after Application path)
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private EventRepository eventRepository;
    @PostMapping(path="/addReservation")
    public  @ResponseBody ResponseEntity<?> addNewReservation(@Valid @RequestBody ReservationEntity reservation){
        reservationRepository.save(reservation);
        return ResponseEntity.ok(reservation);
    }
    @GetMapping(path = "/allReservations")
    public @ResponseBody Iterable<ReservationEntity> getAllReservations(){
        return reservationRepository.findAll();
    }

    @GetMapping("/reservations/{reservationId}")
    public ReservationEntity getReservation(@PathVariable int reservationId) {
        ReservationEntity reservation = reservationRepository.findById(reservationId);


        if (reservation == null) {
            throw new ReservationNotFoundException("Not found reservation by id: " + reservationId);
        }

        return reservation;
    }
    @GetMapping("reservations/for-user/{userId}")
    public  @ResponseBody ResponseEntity<List<ReservationEntity>> getReservationsForUser(@PathVariable Integer userId, HttpServletRequest request) {
        List<ReservationEntity> reservations = reservationRepository.findByUserId(userId);
        request.setAttribute("userId", userId);
        if (reservations.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(reservations);
        }
    }
}
