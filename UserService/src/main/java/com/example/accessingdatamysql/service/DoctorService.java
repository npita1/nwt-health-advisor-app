package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.Role;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.repository.DoctorInfoRepository;
import com.example.accessingdatamysql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final UserRepository userRepository;
    private final DoctorInfoRepository doctorInfoRepository;
    private final PasswordEncoder passwordEncoder;


    public DoctorInfoEntity addDoctor(UserEntity user, DoctorInfoEntity doctorInfo) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.DOCTOR);
        userRepository.save(user);
        doctorInfo.setUser(user);
        return doctorInfoRepository.save(doctorInfo);
    }
}