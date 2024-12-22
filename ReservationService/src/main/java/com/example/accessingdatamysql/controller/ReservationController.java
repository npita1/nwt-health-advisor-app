package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.dto.ReservationEventDTO;
import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.EventEntity;
import com.example.accessingdatamysql.entity.ReservationEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.exceptions.EventNotFoundException;
import com.example.accessingdatamysql.exceptions.ReservationNotFoundException;

import com.example.accessingdatamysql.feign.UserInterface;
import com.example.accessingdatamysql.repository.DoctorInfoRepository;
import com.example.accessingdatamysql.repository.EventRepository;
import com.example.accessingdatamysql.repository.ReservationRepository;
import com.example.accessingdatamysql.repository.UserRepository;
import com.example.accessingdatamysql.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DoctorInfoRepository doctorInfoRepository;
    @Autowired
    private ReservationService reservationService;

    @Autowired
    UserInterface userClient;
    @PostMapping(path="/addReservation")
    public  @ResponseBody ResponseEntity<?> addNewReservation(@Valid @RequestBody ReservationEntity reservation){
        UserEntity user = userClient.getUserByID(reservation.getUser().getId().intValue());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Korisnik s ID-om " + user.getId() + " nije pronađen.");
        }
        UserEntity forumUser = userRepository.findByUserServiceId(user.getId());
        if (forumUser == null) {
            forumUser = new UserEntity();
            forumUser.setUserServiceId(user.getId());
            forumUser.setEmail(user.getEmail());
            forumUser.setFirstName(user.getFirstName());
            forumUser.setLastName(user.getLastName());
            forumUser.setPassword(user.getPassword());
            userRepository.save(forumUser);
            DoctorInfoEntity doctor = userClient.getDoctorByUserId1(user.getId().intValue());
            if(doctor!=null){
                DoctorInfoEntity forumDoctor = new DoctorInfoEntity();
                forumDoctor.setAbout(doctor.getAbout());
                forumDoctor.setSpecialization(doctor.getSpecialization());
                forumDoctor.setPhoneNumber(doctor.getPhoneNumber());

                forumDoctor.setUser(forumUser);
                doctorInfoRepository.save(forumDoctor);
            }
        }
        ReservationEntity newReservation= reservationService.addReservation(reservation,forumUser);
        return ResponseEntity.ok(newReservation);
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
    @DeleteMapping(path = "/deleteReservation/{reservationId}")
    public @ResponseBody ResponseEntity<?> deleteReservation(@PathVariable Long reservationId) {
        try {
            reservationService.deleteReservation(reservationId);
            return ResponseEntity.ok("Rezervacija uspješno obrisana.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/myReservations")
    public List<ReservationEventDTO> getUserReservations() {
        return reservationService.getUserReservations();
    }
}
