package com.example.accessingdatamysql.feign;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.exceptions.UserNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "USERSERVICE")
public interface UserInterface {
    @GetMapping(path="/user/users/{userID}")
    public UserEntity getUserByID(@PathVariable int userID);
    @GetMapping(path="/user/users/firstname/{firstname}")
    public List<UserEntity> getUsersByFirstName(@PathVariable String firstname);

    @GetMapping(path="/user/doctors/{doctorID}")
    public DoctorInfoEntity getDoctorID(@PathVariable int doctorID);
    @GetMapping(path="/user/doctors/specialist/{specialization}")
    public List<DoctorInfoEntity> getDoctorsBySpecialization(@PathVariable String specialization);
}
