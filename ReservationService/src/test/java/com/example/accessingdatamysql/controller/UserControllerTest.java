package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.ReservationEntity;
import com.example.accessingdatamysql.entity.UserEntity;

import com.example.accessingdatamysql.repository.DoctorInfoRepository;
import com.example.accessingdatamysql.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)

public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;


    @MockBean
    private DoctorInfoRepository doctorInfoRepository;

    @InjectMocks
    private UserController userController;
    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private UserEntity user,user1;
    private DoctorInfoEntity doctor;

    private void setup(){
        // Kreiramo testnog korisnika
        user = new UserEntity("test@example.com", "John", "Doe", 1, "passwordHash");
        user1 = new UserEntity("test1@example.com", "James", "Terry", 1, "passwordHash1234");
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(user);
        // Kreiramo testnog liječnika povezanog s korisnikom
        doctor = new DoctorInfoEntity();
        doctor.setId(1L);
        doctor.setAbout("About doctor");
        doctor.setUser(user); // Povezujemo liječnika s korisnikom
        when(doctorInfoRepository.findById(1L)).thenReturn(doctor);


    }
    @Test
    public void testAddNewUser() throws Exception {
        when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity());
        mockMvc.perform(MockMvcRequestBuilders.post("/nwt/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", "test@example.com")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("type", "1")
                        .param("passwordHash", "passwordHash"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Saved"));
    }


    @Test
    public void testGetAllUsers() throws Exception {
        when(userRepository.findAll()).thenReturn(List.of(new UserEntity("test@example.com", "John", "Doe", 1, "passwordHash")));
        mockMvc.perform(MockMvcRequestBuilders.get("/nwt/allUsers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));
    }





}
