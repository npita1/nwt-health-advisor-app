package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.Role;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.exceptions.ErrorDetails;
import com.example.accessingdatamysql.exceptions.UserNotFoundException;
import com.example.accessingdatamysql.repository.DoctorInfoRepository;
import com.example.accessingdatamysql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.accessingdatamysql.exceptions.ErrorDetails;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final UserRepository userRepository;
    private final DoctorInfoRepository doctorInfoRepository;
    private final PasswordEncoder passwordEncoder;


    public DoctorInfoEntity addDoctor(UserEntity user, DoctorInfoEntity doctorInfo) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Doktor s ovom email adresom već postoji.");
        }

        if (doctorInfoRepository.existsByPhoneNumber(doctorInfo.getPhoneNumber())) {
            throw new IllegalArgumentException("Doktor s ovim brojem telefona već postoji.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.DOCTOR);
        userRepository.save(user);
        doctorInfo.setUser(user);
        return doctorInfoRepository.save(doctorInfo);
    }

    public DoctorInfoEntity getDoctorByUserId(int userId) {
        // Dohvati trenutno autentifikovanog korisnika iz SecurityContext-a
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity authenticatedUser = (UserEntity) authentication.getPrincipal();

        // Provjera da li doktor postoji
        DoctorInfoEntity doctor = doctorInfoRepository.getDoctorByUserId(userId);
        if (doctor == null) {
            throw new UserNotFoundException("Doktor nije pronađen za ID: " + userId);
        }

        // Provjera ovlaštenja
        boolean isAdmin = authenticatedUser.getRole() == Role.ADMIN;
        boolean isAuthorizedUser = authenticatedUser.getId() == userId;

        if (!isAdmin && !isAuthorizedUser) {
            throw new SecurityException("Nemate pravo da obrišete ovu rezervaciju.");
        }

        return doctor;
    }
}