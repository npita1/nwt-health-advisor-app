package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.entity.EventEntity;
import com.example.accessingdatamysql.entity.ReservationEntity;
import com.example.accessingdatamysql.exceptions.EventNotFoundException;
import com.example.accessingdatamysql.exceptions.ReservationNotFoundException;
import com.example.accessingdatamysql.repository.EventRepository;
import com.example.accessingdatamysql.repository.ReservationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController// This means that this class is a Controller
@Validated
@RequestMapping(path="/nwt") // This means URL's start with /demo (after Application path)
public class ReservationController {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private EventRepository eventRepository;
    @PostMapping(path="/addReservation")
    public  @ResponseBody String addNewReservation(@Valid @RequestBody ReservationEntity reservation){
        reservationRepository.save(reservation);
        return "Saved";
    }
    @GetMapping(path = "/allReservations")
    public @ResponseBody Iterable<ReservationEntity> getAllReservations(){
        return reservationRepository.findAll();
    }

    @GetMapping("/reservations/{reservationId}")
    public ReservationEntity getReservation(@PathVariable int reservationId) {
        ReservationEntity reservation = reservationRepository.findById(reservationId);


        if (reservation == null) {
            throw new ReservationNotFoundException(" Not found reservation by id: " + reservationId);
        }

        return reservation;
    }
}
