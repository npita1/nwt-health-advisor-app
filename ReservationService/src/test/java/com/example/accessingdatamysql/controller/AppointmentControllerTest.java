package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.controller.AppointmentController;
import com.example.accessingdatamysql.entity.AppointmentEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.exceptions.AppointmentNotFoundException;
import com.example.accessingdatamysql.feign.UserInterface;
import com.example.accessingdatamysql.repository.AppointmentRepository;
import com.example.accessingdatamysql.repository.DoctorInfoRepository;
import com.example.accessingdatamysql.repository.UserRepository;
import com.example.accessingdatamysql.service.AppointmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    @MockBean
    private AppointmentService appointmentService;
    @MockBean
    private UserInterface userClient;
    @InjectMocks
    private AppointmentController appointmentController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testAddNewAppointment() throws Exception {
        AppointmentEntity appointment = new AppointmentEntity("Test appointment");

        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(appointment);

        mockMvc.perform(post("/reservation/addAppointment1")
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

        mockMvc.perform(get("/reservation/allAppointments"))
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

        mockMvc.perform(get("/reservation/appointments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Test appointment"));

        verify(appointmentRepository, times(1)).findById(1L);
    }
    @Test
    public void getAppointmentUnSuccessfully1() throws Exception {
        mockMvc.perform(get(String.format("/reservation/appointments/%d", -1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("not_found"))
                .andExpect(jsonPath("$.message").value("Not found appointment by id -1"));
    }

    @Test
    void addAppointmentSuccessfully() throws Exception {
        mockMvc.perform(post("/reservation/addAppointment1")
                        .content("\"description\":\"opis_neki\"")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void addAppointmentUnSuccessfully1() throws Exception {
        mockMvc.perform(post("/reservation/addAppointment1")
                        .content("{\"description\": \"\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Description must not be blank"));
    }
    @Test
    public void testAddAppointment_Success() {
        // Setup
        int userId = 1;
        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setDescription("Test Description");
        // Mock-ovanje metode getUserByID() Feign klijenta da vrati testnog korisnika
        when(userClient.getUserByID(userId)).thenReturn(new UserEntity("test@example.com", "John", "Doe", 1, "passwordHash"));
        // Mock-ovanje metode save() repozitorijuma za čuvanje appointmenta
        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(appointment);

        // Test
        ResponseEntity<?> response = appointmentController.addAppointment(userId, appointment);

        // Verify
        // Provjera da li je odgovor uspješan (HTTP status kod OK)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Provjera da li je tijelo odgovora tipa AppointmentEntity i da li je isto kao i proslijeđeni appointment
        assertTrue(response.getBody() instanceof AppointmentEntity);
        assertEquals(appointment, response.getBody());
    }

    @Test
    public void testAddAppointment_Success2() throws Exception {
        // Setup
        int userId = 1;
        UserEntity user = new UserEntity("test@example.com", "John", "Doe", 1, "passwordHash");
        // Mock-ovanje metode getUserByID() Feign klijenta da vrati testnog korisnika
        when(userClient.getUserByID(userId)).thenReturn(user);
        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setDescription("Test Description");
        // Mock-ovanje metode save() repozitorijuma za čuvanje appointmenta
        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(appointment);

        // Perform request and assert
        // Izvršavanje zahtjeva i provjera
        mockMvc.perform(MockMvcRequestBuilders.post("/reservation/addAppointment")
                        .param("userId", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(appointment)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test Description"));
    }

    @Test
    public void testAddAppointment_FeignException() throws Exception {
        // Setup
        int userId = 1;
        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setDescription("Test Description");
        // Mock-ovanje metode getUserByID() Feign klijenta da baci FeignException
        when(userClient.getUserByID(userId)).thenThrow(FeignException.class);

        // Perform request and assert
        // Izvršavanje zahtjeva i provjera
        mockMvc.perform(MockMvcRequestBuilders.post("/reservation/addAppointment")
                        .param("userId", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(appointment)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("Failed to communicate with the remote service."));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
