package com.example.accessingdatamysql.controllers;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.repository.DoctorInfoRepository;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/nwt")
public class MainController {

}