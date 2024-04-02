package com.example.accessingdatamysql.controllers;

import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.exceptions.UserNotFoundException;
import com.example.accessingdatamysql.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/nwt")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/addUser")
    public ResponseEntity<String> addNewDoctor(@Valid @RequestBody UserEntity user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed: \n");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append(";\n");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
        userRepository.save(user);
        return ResponseEntity.ok("User created successfully");
    }

    @GetMapping(path="/allUsers")
    public @ResponseBody Iterable<UserEntity> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @GetMapping(path="/users/{userID}")
    public UserEntity getUserByID(@PathVariable int userID){
        UserEntity user = userRepository.findById(userID);

        if(user == null) {
        throw new UserNotFoundException("Not found user by id: " + userID);
    }

    //return ResponseEntity.ok(user);
        return user;
}

@GetMapping(path="/users/lastname/{lastname}")
public List<UserEntity> getUsersByLastName(@PathVariable String lastname){
    List<UserEntity> users = userRepository.findByLastName(lastname);

    if(users.isEmpty()) {
        throw new UserNotFoundException("Not found user with name: " + lastname);
    }

    return users;
}

@GetMapping(path="/users/firstname/{firstname}")
    public List<UserEntity> getUsersByFirstName(@PathVariable String firstname){
        List<UserEntity> users = userRepository.findByFirstName(firstname);

        if(users.isEmpty()) {
            throw new UserNotFoundException("Not found user with name: " + firstname);
        }

        return users;
    }



}
