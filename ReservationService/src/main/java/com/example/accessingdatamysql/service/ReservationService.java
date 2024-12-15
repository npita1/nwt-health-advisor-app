package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.ReservationEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
       reservation.setUser(user);
        return this.reservationRepository.save(reservation);

    }
}
