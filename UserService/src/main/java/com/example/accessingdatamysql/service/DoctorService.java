package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.dto.DoctorDTO;
import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.Role;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.exceptions.ErrorDetails;
import com.example.accessingdatamysql.exceptions.UserNotFoundException;
import com.example.accessingdatamysql.repository.DoctorInfoRepository;
import com.example.accessingdatamysql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.accessingdatamysql.exceptions.ErrorDetails;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final UserRepository userRepository;
    private final DoctorInfoRepository doctorInfoRepository;
    private final PasswordEncoder passwordEncoder;
    public void validateImagePath(String imagePath) {
        if (imagePath == null || (!imagePath.endsWith(".png") && !imagePath.endsWith(".jpg"))) {
            throw new IllegalArgumentException("Slika mora biti u .jpg ili .png formatu");
        }
    }

    public DoctorInfoEntity addDoctor(UserEntity user, DoctorInfoEntity doctorInfo) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Doktor s ovom email adresom već postoji.");
        }

        if (doctorInfoRepository.existsByPhoneNumber(doctorInfo.getPhoneNumber())) {
            throw new IllegalArgumentException("Doktor s ovim brojem telefona već postoji.");
        }
        validateImagePath(doctorInfo.getImagePath());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.DOCTOR);
        userRepository.save(user);
        doctorInfo.setUser(user);
        return doctorInfoRepository.save(doctorInfo);
    }

    public DoctorInfoEntity getDoctorByUserId(int userId) {
        // Provjera da li doktor postoji
        DoctorInfoEntity doctor = doctorInfoRepository.getDoctorByUserId(userId);
        if (doctor == null) {
            return null;
        }

        // Dohvati trenutno autentifikovanog korisnika iz SecurityContext-a
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity authenticatedUser = (UserEntity) authentication.getPrincipal();


        // Provjera ovlaštenja
        boolean isAdmin = authenticatedUser.getRole() == Role.ADMIN;
        boolean isAuthorizedUser = authenticatedUser.getId() == userId;

        if (!isAdmin && !isAuthorizedUser) {
            return null;
        }

        return doctor;
    }
    public Iterable<DoctorDTO> getAllDoctorsAsDTO() {
        List<DoctorInfoEntity> doctors = new ArrayList<>();
        doctorInfoRepository.findAll().forEach(doctors::add);

        return doctors.stream().map(doctor -> {
            DoctorDTO dto = new DoctorDTO();
            dto.setId(doctor.getId());
            dto.setFirstName(doctor.getUser().getFirstName());
            dto.setLastName(doctor.getUser().getLastName());
            dto.setEmail(doctor.getUser().getEmail());
            dto.setPhoneNumber(doctor.getPhoneNumber());
            dto.setSpecialization(doctor.getSpecialization());
            dto.setAvailability(doctor.getAvailability());
            dto.setAbout(doctor.getAbout());
            dto.setImagePath(doctor.getImagePath());
            return dto;
        }).collect(Collectors.toList());
    }
}