package com.example.accessingdatamysql.feign;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.exceptions.UserNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "USERSERVICE", configuration = FeignClientConfiguration.class)
public interface UserInterface {
    @GetMapping(path="/user/users/{userID}")
    public UserEntity getUserByID(@PathVariable int userID);
    @GetMapping(path="/user/users/firstname/{firstname}")
    public List<UserEntity> getUsersByFirstName(@PathVariable String firstname);

    @GetMapping(path="/user/doctors/{doctorID}")
    public DoctorInfoEntity getDoctorID(@PathVariable int doctorID);
    @GetMapping(path="/user/doctors/specialist/{specialization}")
    public List<DoctorInfoEntity> getDoctorsBySpecialization(@PathVariable String specialization);

    @GetMapping(path="/user/getDoctorByUserid/{userID}")
    public DoctorInfoEntity getDoctorByUserId1(@PathVariable int userID);
    @GetMapping("/api/tokens/validate")
    boolean isTokenValid(@RequestParam("token") String token);

}
