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
public class EventController {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @PostMapping(path="/addEvent")
    public  @ResponseBody String addNewEvent(@Valid @RequestBody EventEntity event){
        eventRepository.save(event);
        return "Saved";
    }
    @GetMapping(path = "/allEvents")
    public @ResponseBody Iterable<EventEntity> getAllEvents(){
        return eventRepository.findAll();
    }
    @GetMapping("/events/{eventId}")
    public EventEntity getEvent(@PathVariable int eventId) {
        EventEntity event = eventRepository.findById(eventId);


        if (event == null) {
            throw new ReservationNotFoundException("Not found event by id " + eventId);
        }

        return event;
    }
    @GetMapping(path="/reservations-for-events/{eventId}")
    public List<ReservationEntity> getReservationsForEvent(@PathVariable int eventId){
        EventEntity event=eventRepository.findById(eventId);
        if(event==null){
            throw new EventNotFoundException("Not found event by id "+ eventId);
        }
        return reservationRepository.findByEvent(event);
    }
}
