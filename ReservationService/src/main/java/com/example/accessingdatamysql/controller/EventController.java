package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.entity.AppointmentEntity;
import com.example.accessingdatamysql.entity.EventEntity;
import com.example.accessingdatamysql.entity.ReservationEntity;
import com.example.accessingdatamysql.exceptions.EventNotFoundException;
import com.example.accessingdatamysql.exceptions.ReservationNotFoundException;
import com.example.accessingdatamysql.feign.ForumInterface;
import com.example.accessingdatamysql.repository.EventRepository;
import com.example.accessingdatamysql.repository.ReservationRepository;
import com.example.accessingdatamysql.service.AppointmentService;
import com.example.accessingdatamysql.service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController// This means that this class is a Controller
@Validated
@RequestMapping(path="/nwt") // This means URL's start with /demo (after Application path)
public class EventController {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private EventService eventService;
    @Autowired
    private ForumInterface forumClient;
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
    @PatchMapping(path = "/events/{id}", consumes = "application/json-patch+json")
    public @ResponseBody ResponseEntity Update(@PathVariable("id") Long id, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        EventEntity event = eventService.Details(id);
        EventEntity eventPatched = applyPatchToEvent(patch, event);
        eventService.Update(eventPatched);
        return ResponseEntity.status(200).body(eventPatched);

    }

    private EventEntity applyPatchToEvent(JsonPatch patch, EventEntity targetEvent) throws JsonProcessingException, JsonPatchException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetEvent, JsonNode.class));
        return objectMapper.treeToValue(patched, EventEntity.class);
    }

    @PutMapping("/eventHeld/{eventId}")
    public @ResponseBody ResponseEntity<String> handleEventConclusion(@PathVariable Long eventId) {
        EventEntity event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found"));

        Long doctorId = event.getDoctorInfo().getId();
        Long categoryId = 1L;

        String title = "Članak o eventu pod nazivom:  " + event.getName() +", " + " održanom na lokaciji: "+ event.getLocation();
        String text = "Osvrt na događaj: "+ event.getDescription();

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        forumClient.addArticle1(doctorId, categoryId, title, text, date);

        return ResponseEntity.ok("Event concluded and article created.");
    }

}
