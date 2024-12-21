package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.EventEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.repository.DoctorInfoRepository;
import com.example.accessingdatamysql.repository.EventRepository;
import com.example.accessingdatamysql.repository.ReservationRepository;
import com.example.accessingdatamysql.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorInfoRepository doctorInfoRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Transactional
    public void deleteUser(long userServiceId) {
        //Pronađi usera kojeg treba obrisati
        UserEntity user = userRepository.findByUserServiceId(userServiceId);
        // Provjera ako korisnik nije pronađen
        if (user != null) {


            DoctorInfoEntity doctor = doctorInfoRepository.findByUserId(user.getId());
            if (doctor != null) {
                Iterable<EventEntity> events = eventRepository.findAllByDoctorInfo(doctor);
                for (EventEntity event : events) {
                    reservationRepository.deleteAllByEvent(event);
                }
                eventRepository.deleteAllByDoctorInfo(doctor);
                doctorInfoRepository.delete(doctor);
            }
            reservationRepository.deleteAllByUser(user);
            userRepository.delete(user);
        }
    }
}
