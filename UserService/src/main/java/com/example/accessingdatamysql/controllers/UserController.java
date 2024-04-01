package com.example.accessingdatamysql.controllers;

import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/nwt")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/addUser")
    public ResponseEntity<String> addNewDoctor(@Valid @RequestBody UserEntity user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation failed: " + bindingResult.getAllErrors());
        }
        userRepository.save(user);
        return ResponseEntity.ok("User created successfully");
    }

    @GetMapping(path="/allUsers")
    public @ResponseBody Iterable<UserEntity> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

}
