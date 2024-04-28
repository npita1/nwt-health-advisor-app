package com.example.accessingdatamysql.controllers;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.exceptions.UserNotFoundException;
import com.example.accessingdatamysql.repository.DoctorInfoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/nwt")
public class DoctorInfoController {

    @Autowired
    private DoctorInfoRepository doctorInfoRepository;

    @PostMapping(path="/addDoctor") // Map ONLY POST Requests
    public @ResponseBody ResponseEntity<String> addNewDoctor (@Valid @RequestBody DoctorInfoEntity doctor, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation failed: " + bindingResult.getAllErrors());
        }
        doctorInfoRepository.save(doctor);
        return ResponseEntity.ok("Doctor cretaed successfully");
    }

    @GetMapping(path="/allDoctors")
    public @ResponseBody Iterable<DoctorInfoEntity> getAllDoctors() {
        // This returns a JSON or XML with the users
        return doctorInfoRepository.findAll();
    }
    @GetMapping(path="/doctors/specialist/{specialization}")
    public List<DoctorInfoEntity> getDoctorsBySpecialization(@PathVariable String specialization){

        List<DoctorInfoEntity> users = doctorInfoRepository.findBySpecialization(specialization);

        if(users.isEmpty()) {
            throw new UserNotFoundException("Not found doctor with specialization: " + specialization);
        }

        return users;
    }

    @GetMapping(path="/doctors/{doctorID}")
    public DoctorInfoEntity getDoctorID(@PathVariable int doctorID){
        DoctorInfoEntity doctor = doctorInfoRepository.findById(doctorID);

        if(doctor == null) {
            throw new UserNotFoundException("Not found doctor by id: " + doctorID);
        }

        //return ResponseEntity.ok(user);
        return doctor;
    }

}
