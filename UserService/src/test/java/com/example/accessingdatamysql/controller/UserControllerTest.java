package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.controllers.UserController;
import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAddNewDoctor() throws Exception {
        // Create a sample user
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setType(1);
        user.setPasswordHash("passwordHash123##");

        // Simulate no validation errors
        when(userRepository.save(user)).thenReturn(user);

        // Perform the POST request
        mvc.perform(MockMvcRequestBuilders.post("/nwt/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"type\":\"1\",\"passwordHash\":\"passwordHash123##\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User created successfully"));
    }


    @Test
    public void testAddNewDoctor2() throws Exception {
        // Create a sample user
        UserEntity user = new UserEntity();
        user.setEmail("wrongemail");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setType(1);
        user.setPasswordHash("passwordHash123##");

        // Simulate no validation errors
        when(userRepository.save(user)).thenReturn(user);

        // Perform the POST request
        mvc.perform(MockMvcRequestBuilders.post("/nwt/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"wrongemail\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"type\":\"1\",\"passwordHash\":\"passwordHash123##\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
               // .andExpect(MockMvcResultMatchers.content().string("User created successfully"));
    }
}
