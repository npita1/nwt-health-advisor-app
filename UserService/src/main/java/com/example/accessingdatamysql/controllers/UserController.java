package com.example.accessingdatamysql.controllers;

import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.exceptions.UserNotFoundException;
import com.example.accessingdatamysql.grpc.GrpcClient;
import com.example.accessingdatamysql.repository.UserRepository;
import com.example.accessingdatamysql.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping(path="/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserRepository userRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private static GrpcClient grpcClient;

    @Autowired
    private UserService userService;

    @PostMapping(path = "/addUser")
    public ResponseEntity<String> addNewDoctor(@Valid @RequestBody UserEntity user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed: \n");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append(";\n");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
        userRepository.save(user);
        return ResponseEntity.ok("User created successfully");
    }

    @GetMapping(path = "/allUsers")
    public @ResponseBody Iterable<UserEntity> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @GetMapping(path = "/users/{userID}")
    public UserEntity getUserByID(@PathVariable int userID, HttpServletRequest request) {


        String authHeader = request.getHeader("Authorization");
        System.out.println("Received Authorization header: " + authHeader);

        UserEntity user = userRepository.findById(userID);

        if (user == null) {
            GrpcClient.get().log(userID, "User", "GET", "No user found by id");
            throw new UserNotFoundException("Not found user by id: " + userID);
        }

        GrpcClient.get().log(userID, "User", "GET", "User found by id");
        //return ResponseEntity.ok(user);
        return user;
    }

    @GetMapping(path = "/users/firstname/{firstname}")
    public List<UserEntity> getUsersByFirstName(@PathVariable String firstname) {
        List<UserEntity> users = userRepository.findByFirstName(firstname);

        if (users.isEmpty()) {
            throw new UserNotFoundException("Not found user with name: " + firstname);
        }

        return users;
    }

    @GetMapping(path = "/users/lastname/{lastname}")
    public List<UserEntity> getUsersByLastName(@PathVariable String lastname) {
        List<UserEntity> users = userRepository.findByLastName(lastname);

        if (users.isEmpty()) {
            throw new UserNotFoundException("Not found user with name: " + lastname);
        }

        return users;
    }

    private UserEntity applyPatchToUser(
            JsonPatch patch, UserEntity targetUser) throws JsonPatchException, JsonProcessingException {

        JsonNode patched = patch.apply(objectMapper.convertValue(targetUser, JsonNode.class));
        return objectMapper.treeToValue(patched, UserEntity.class);
    }



    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<UserEntity> updateUser(@PathVariable long id, @RequestBody JsonPatch patch) {

        try {
            UserEntity user = userRepository.findById(id);
            if (user == null) {
                throw new UserNotFoundException("User not found.");
            }
            UserEntity userPatched = applyPatchToUser(patch, user);
            userRepository.save(userPatched);
            return ResponseEntity.ok(userPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(path = "/users/email/{email}")
    public UserEntity getUserByEmail(@PathVariable String email) {
        UserEntity user = userRepository.findByEmail1(email);

        if (user == null) {
            throw new UserNotFoundException("Not found user with email: " + email);
        }

        return user;
    }
    @GetMapping("/currentUser")
    public Map<String,String> getCurrentUser() {
        Map<String, String> userDetails = userService.getUserDetailsFromAuthentication();
        if (userDetails.isEmpty()) {
            return null;
        }
        return userDetails;
    }
}




