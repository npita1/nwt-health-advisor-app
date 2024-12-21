package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.repository.*;

import com.example.accessingdatamysql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Validated
@CrossOrigin
@RequestMapping(path="/forum")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping(path="/addUser")
    public @ResponseBody String addNewUser (@RequestBody UserEntity user) {
        userRepository.save(user);
        return "UserEntity Saved";
    }

    @GetMapping(path="/allUsers")
    public @ResponseBody Iterable<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }


    @DeleteMapping(path="/deleteUser/{userServiceId}")
    public @ResponseBody ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long userServiceId) {
        try {
            userService.deleteUser(userServiceId);
            Map<String, String> response = Map.of("message", "Korisnik i svi povezani podaci su uspješno obrisani.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = Map.of("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

}