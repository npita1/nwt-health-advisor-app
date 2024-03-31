package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.controller.AppointmentController;
import com.example.accessingdatamysql.entity.AppointmentEntity;
import com.example.accessingdatamysql.exceptions.AppointmentNotFoundException;
import com.example.accessingdatamysql.repository.AppointmentRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentRepository appointmentRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private DoctorInfoRepository doctorInfoRepository;
    @InjectMocks
    private AppointmentController appointmentController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testAddNewAppointment() throws Exception {
        AppointmentEntity appointment = new AppointmentEntity("Test appointment");

        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(appointment);

        mockMvc.perform(post("/nwt/addAppointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointment)))
                .andExpect(status().isOk())
                .andExpect(content().string("Saved"));

        verify(appointmentRepository, times(1)).save(any(AppointmentEntity.class));
    }

    @Test
    public void testGetAllAppointments() throws Exception {
        AppointmentEntity appointment1 = new AppointmentEntity("Test appointment 1");
        AppointmentEntity appointment2 = new AppointmentEntity("Test appointment 2");
        List<AppointmentEntity> appointments = Arrays.asList(appointment1, appointment2);

        when(appointmentRepository.findAll()).thenReturn(appointments);

        mockMvc.perform(get("/nwt/allAppointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].description").value("Test appointment 1"))
                .andExpect(jsonPath("$[1].description").value("Test appointment 2"));

        verify(appointmentRepository, times(1)).findAll();
    }
    @Test
    public void testGetAppointmentById_Success() throws Exception {
        AppointmentEntity appointment = new AppointmentEntity("Test appointment");
        appointment.setId(1L);

        when(appointmentRepository.findById(anyLong())).thenReturn((appointment));

        mockMvc.perform(get("/nwt/appointments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Test appointment"));

        verify(appointmentRepository, times(1)).findById(1L);
    }
    @Test
    public void getAppointmentUnSuccessfully1() throws Exception {
        mockMvc.perform(get(String.format("/nwt/appointments/%d", -1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("not_found"))
                .andExpect(jsonPath("$.message").value("Not found appointment by id -1"));
    }

    @Test
    void addAppointmentSuccessfully() throws Exception {
        mockMvc.perform(post("/nwt/addAppointment")
                        .content("\"description\":\"opis_neki\"")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void addAppointmentUnSuccessfully1() throws Exception {
        mockMvc.perform(post("/nwt/addAppointment")
                        .content("{\"description\": \"\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Description must not be blank"));
    }
}
