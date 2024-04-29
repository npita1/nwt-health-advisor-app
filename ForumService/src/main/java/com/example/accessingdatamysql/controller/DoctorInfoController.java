package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.repository.DoctorInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController// This means that this class is a Controller
@Validated
@RequestMapping(path="/nwt")
public class DoctorInfoController {
    @Autowired
    private DoctorInfoRepository doctorInfoRepository;

    @PostMapping(path="/addDoctor") // Map ONLY POST Requests
    public @ResponseBody String addNewDoctor (@RequestBody DoctorInfoEntity doctor) {
        doctorInfoRepository.save(doctor);
        return "Saved";
    }

    @GetMapping(path="/allDoctors")
    public @ResponseBody Iterable<DoctorInfoEntity> getAllDoctors() {
        return doctorInfoRepository.findAll();
    }

}
