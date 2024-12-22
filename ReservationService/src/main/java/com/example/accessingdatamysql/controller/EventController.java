package com.example.accessingdatamysql.controller;


import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.EventEntity;
import com.example.accessingdatamysql.entity.ReservationEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.exceptions.EventNotFoundException;
import com.example.accessingdatamysql.exceptions.ReservationNotFoundException;
import com.example.accessingdatamysql.feign.ForumInterface;
import com.example.accessingdatamysql.feign.UserInterface;
import com.example.accessingdatamysql.repository.DoctorInfoRepository;
import com.example.accessingdatamysql.repository.EventRepository;
import com.example.accessingdatamysql.repository.ReservationRepository;
import com.example.accessingdatamysql.repository.UserRepository;
import com.example.accessingdatamysql.service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController// This means that this class is a Controller
@Validated
@RequestMapping(path="/reservation") // This means URL's start with /demo (after Application path)
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
    @Autowired
    UserInterface userClient;
    @Autowired
    private DoctorInfoRepository doctorInfoRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping(path="/addEvent")
    public  @ResponseBody ResponseEntity<EventEntity> addNewEvent(@Valid @RequestBody EventEntity event){
        // Dohvatite doctor iz zahtjeva
        DoctorInfoEntity doctor1 = event.getDoctorInfo();
        int did=doctor1.getId().intValue();
        DoctorInfoEntity doctor = userClient.getDoctorID(did);
        // Provjerite da li doctor postoji i modifikujte njegov ID
        if (doctor != null) {
            // Provjera da li je doktor vec spasen u bazi foruma
            DoctorInfoEntity forumDoctor = doctorInfoRepository.findByUserServiceId(doctor.getUser().getId());
            if (forumDoctor == null) {
                forumDoctor = new DoctorInfoEntity();
                UserEntity forumUser = new UserEntity();
                forumUser.setUserServiceId(doctor.getUser().getId());
                forumUser.setEmail(doctor.getUser().getEmail());
                forumUser.setFirstName(doctor.getUser().getFirstName());
                forumUser.setLastName(doctor.getUser().getLastName());
                forumUser.setPassword(doctor.getUser().getPassword());
                userRepository.save(forumUser);

                forumDoctor.setUser(forumUser);
                forumDoctor.setAbout(doctor.getAbout());
                forumDoctor.setSpecialization(doctor.getSpecialization());
                forumDoctor.setPhoneNumber(doctor.getPhoneNumber());
                doctorInfoRepository.save(forumDoctor);
            }
            doctor1.setId(forumDoctor.getId());
        }
        EventEntity eventNew = eventService.addEvent(event);
        return ResponseEntity.ok(eventNew);
    }
    @GetMapping(path = "/allEvents")
    public @ResponseBody Iterable<EventEntity> getAllEvents(HttpServletRequest request){
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


    private EventEntity applyPatchToEvent(JsonPatch patch, EventEntity targetEvent) throws JsonProcessingException, JsonPatchException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetEvent, JsonNode.class));
        return objectMapper.treeToValue(patched, EventEntity.class);
    }

    @PutMapping("/eventHeld/{eventId}")
    public @ResponseBody ResponseEntity<String> handleEventConclusion(@PathVariable Long eventId) {
        try {
            EventEntity event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new EventNotFoundException("Event not found"));

            Long doctorId = event.getDoctorInfo().getId();
            Long categoryId = 1L;

            String title = "Članak o eventu pod nazivom: " + event.getName() + ", održanom na lokaciji: " + event.getLocation();
            String text = "Osvrt na događaj: " + event.getDescription();

            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

            forumClient.addArticle1(doctorId, categoryId, title, text, date);

            return ResponseEntity.ok("Event concluded and article created.");
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to communicate with the remote service.");
        }
    }
    @DeleteMapping(path = "/deleteEvent/{eventId}")
    public @ResponseBody ResponseEntity<Map<String, String>> deleteEvent(@PathVariable Long eventId) {
        try {
            eventService.deleteEvent(eventId);
            Map<String, String> response = Map.of("message", "Pitanje i svi povezani odgovori su uspješno obrisani.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Vraćamo grešku u JSON formatu
            Map<String, String> errorResponse = Map.of("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

}
