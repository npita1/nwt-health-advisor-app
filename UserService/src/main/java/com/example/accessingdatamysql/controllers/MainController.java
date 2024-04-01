package com.example.accessingdatamysql.controllers;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.repository.DoctorInfoRepository;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/nwt")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorInfoRepository doctorInfoRepository;

    @PostMapping(path="/addUser")
    public ResponseEntity<String> addNewDoctor(@Valid @RequestBody UserEntity user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation failed: " + bindingResult.getAllErrors());
        }
        userRepository.save(user);
        return ResponseEntity.ok("User created successfully");
    }

    @PostMapping(path="/addDoctor") // Map ONLY POST Requests
    public @ResponseBody ResponseEntity<String> addNewDoctor (@Valid @RequestBody DoctorInfoEntity doctor, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation failed: " + bindingResult.getAllErrors());
        }
        doctorInfoRepository.save(doctor);
        return ResponseEntity.ok("Doctor cretade successfully");
    }

    @GetMapping(path="/allUsers")
    public @ResponseBody Iterable<UserEntity> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @GetMapping(path="/allDoctors")
    public @ResponseBody Iterable<DoctorInfoEntity> getAllDoctors() {
        // This returns a JSON or XML with the users
        return doctorInfoRepository.findAll();
    }
}