package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.ReservationEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public ReservationEntity addReservation(ReservationEntity reservation, UserEntity user){
        if (user.getId() == null) {
            throw new IllegalArgumentException("User must be saved in the database before adding a forum question.");
        }

        // Validacija broja karata
        if (reservation.getNumOfTicket() == null) {
            throw new IllegalArgumentException("Number of tickets is mandatory.");
        }
        if (reservation.getNumOfTicket() < 1) {
            throw new IllegalArgumentException("The number of tickets must be a positive integer.");
        }

        // Provjera postoji li već rezervacija za korisnika i odabrani događaj
        if (reservationRepository.findByEventIdAndUser(reservation.getEvent().getId(), user).isPresent()) {
            throw new IllegalArgumentException("Već imate rezervaciju za ovaj događaj");
        }
       reservation.setUser(user);
        return this.reservationRepository.save(reservation);

    }
    public void deleteReservation(Long id) {
        // Dohvati trenutnog korisnika iz SecurityContextHolder
        String currentUserEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Pronađi rezervaciju
        ReservationEntity reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rezervacija s ID-jem " + id + " nije pronađena."));

        // Provjeri da li trenutni korisnik ima pravo na ovu rezervaciju
        if (!reservation.getUser().getEmail().equals(currentUserEmail)) {
            throw new SecurityException("Nemate pravo da obrišete ovu rezervaciju.");
        }

        // Obriši rezervaciju ako je provjera uspješna
        reservationRepository.delete(reservation);
    }
}
