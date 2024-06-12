package com.example.accessingdatamysql.controllers;

import com.example.accessingdatamysql.auth.DoctorRequest;
import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.exceptions.UserNotFoundException;
import com.example.accessingdatamysql.feign.ForumInterface;
import com.example.accessingdatamysql.repository.DoctorInfoRepository;
import com.example.accessingdatamysql.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(path="/user")
@RequiredArgsConstructor
public class DoctorInfoController {

    @Autowired
    ForumInterface forumClient;
    @Autowired
    private DoctorInfoRepository doctorInfoRepository;
    private final DoctorService doctorService;
    @PostMapping(path="/addDoctor") // Map ONLY POST Requests
    public @ResponseBody ResponseEntity<String> addNewDoctor (@Valid @RequestBody DoctorInfoEntity doctor, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation failed: " + bindingResult.getAllErrors());
        }
        doctorInfoRepository.save(doctor);
        return ResponseEntity.ok("Doctor cretaed successfully");
    }

//        @PostMapping(path="/addDoctor", consumes = "multipart/form-data") // Map ONLY POST Requests
//    public @ResponseBody ResponseEntity<String> addNewDoctor (@Valid @RequestBody DoctorInfoEntity doctor,
//                                                              BindingResult bindingResult,
//                                                              @RequestParam("image") @ModelAttribute MultipartFile image) {
//            // Spremanje slike u folder
//            String imagePath = null;
//            if (!image.isEmpty()) {
//                try {
//                    byte[] bytes = image.getBytes();
//                    Path path = Paths.get("uploads/" + image.getOriginalFilename());
//                    Files.write(path, bytes);
//                    imagePath = "/uploads/" + image.getOriginalFilename(); // Relativna putanja
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Greška pri spremanju slike: " + e.getMessage());
//                }
//            }
//        if(bindingResult.hasErrors()) {
//            return ResponseEntity.badRequest().body("Validation failed: " + bindingResult.getAllErrors());
//        }
//        doctor.setImagePath(imagePath);
//        doctorInfoRepository.save(doctor);
//        return ResponseEntity.ok("Doctor cretaed successfully");
//    }


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
    @GetMapping(path="/doctor/getbyuserid")
    public DoctorInfoEntity getDoctorByUserId(@RequestParam("doctorId") int userID){
        DoctorInfoEntity doctor = doctorInfoRepository.getDoctorByUserId(userID);

        if(doctor == null) {
            throw new UserNotFoundException("Not found doctor by id: " + userID);
        }

        //return ResponseEntity.ok(user);
        return doctor;
    }

    @GetMapping(path="/doctor/{doctorID}/articles")
    public @ResponseBody  Map<String, String> getArticlesForDoctor(@PathVariable int doctorID){
        return  forumClient.getTitleAndTextArticleDoctorId(doctorID);

    }
    @PostMapping(path = "/addNewDoctor", consumes = "multipart/form-data")
    public ResponseEntity<DoctorInfoEntity> addDoctor(@Valid @RequestParam("email") String email,
                                                      @Valid @RequestParam("firstName") String firstName,
                                                      @Valid @RequestParam("lastName") String lastName,
                                                      @Valid @RequestParam("password") String password,
                                                      @Valid @RequestParam("about") String about,
                                                      @Valid @RequestParam("specialization") String specialization,
                                                      @Valid @RequestParam("availability") String availability,
                                                      @Valid @RequestParam("phoneNumber") String phoneNumber,
                                                      @RequestParam("image") @ModelAttribute MultipartFile image
    ) {
        // Spremanje slike u folder
        String imagePath = null;
        if (!image.isEmpty()) {
            try {
                byte[] bytes = image.getBytes();
                Path path = Paths.get("uploads/" + image.getOriginalFilename());
                Files.write(path, bytes);
                imagePath = "/uploads/" + image.getOriginalFilename(); // Relativna putanja
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);

        DoctorInfoEntity doctorInfo = new DoctorInfoEntity();
        doctorInfo.setAbout(about);
        doctorInfo.setSpecialization(specialization);
        doctorInfo.setAvailability(availability);
        doctorInfo.setPhoneNumber(phoneNumber);
        doctorInfo.setImagePath(imagePath);

        DoctorInfoEntity savedDoctorInfo = doctorService.addDoctor(user, doctorInfo);
        return ResponseEntity.ok(savedDoctorInfo);
    }


}
