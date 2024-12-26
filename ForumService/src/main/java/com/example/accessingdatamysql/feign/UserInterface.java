package com.example.accessingdatamysql.feign;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "USERSERVICE",configuration = FeignClientConfiguration.class)
public interface UserInterface {

    @GetMapping(value = "/user/doctors/{doctorID}")
    public DoctorInfoEntity getDoctorID(@PathVariable int doctorID);

    @GetMapping(path="/user/users/{userID}")
    public UserEntity getUserByID(@PathVariable int userID);


    @GetMapping("/api/tokens/validate")
    boolean isTokenValid(@RequestParam("token") String token);

    @GetMapping(path="/user/getDoctorByUserid/{userID}")
    public DoctorInfoEntity getDoctorByUserId1(@PathVariable int userID);

}
