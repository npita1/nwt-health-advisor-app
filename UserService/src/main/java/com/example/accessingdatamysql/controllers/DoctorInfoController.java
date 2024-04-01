package com.example.accessingdatamysql.controllers;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.repository.DoctorInfoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok("Doctor cretade successfully");
    }

    @GetMapping(path="/allDoctors")
    public @ResponseBody Iterable<DoctorInfoEntity> getAllDoctors() {
        // This returns a JSON or XML with the users
        return doctorInfoRepository.findAll();
    }
}
