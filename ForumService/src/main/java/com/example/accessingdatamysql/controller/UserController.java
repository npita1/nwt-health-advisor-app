package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
@RequestMapping(path="/forum")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @PostMapping(path="/addUser")
    public @ResponseBody String addNewUser (@RequestBody UserEntity user) {
        userRepository.save(user);
        return "UserEntity Saved";
    }

    @GetMapping(path="/allUsers")
    public @ResponseBody Iterable<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

}