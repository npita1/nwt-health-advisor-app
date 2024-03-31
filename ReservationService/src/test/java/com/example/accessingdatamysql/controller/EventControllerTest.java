package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.EventEntity;
import com.example.accessingdatamysql.entity.ReservationEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.repository.DoctorInfoRepository;
import com.example.accessingdatamysql.repository.EventRepository;
import com.example.accessingdatamysql.repository.ReservationRepository;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventRepository eventRepository;
    @MockBean
    private DoctorInfoRepository doctorInfoRepository;
    @MockBean
    private ReservationRepository reservationRepository;
    @InjectMocks
    private EventController eventController;

    @Test
    public void testAddNewEventSuccessfully() throws Exception {
        // Kreiramo testnog korisnika
        UserEntity user = new UserEntity("test@example.com", "John", "Doe", 1, "passwordHash");

        // Kreiramo testnog liječnika povezanog s korisnikom
        DoctorInfoEntity doctor = new DoctorInfoEntity();
        doctor.setId(1L);
        doctor.setAbout("About doctor");
        doctor.setUser(user); // Povezujemo liječnika s korisnikom
        when(doctorInfoRepository.findById(1L)).thenReturn(doctor);
        EventEntity event = new EventEntity("Test Event", "Description", "06.04.2024", "Location");
        event.setDoctorInfo(doctor);
        when(eventRepository.save(any(EventEntity.class))).thenReturn(event);

        mockMvc.perform(MockMvcRequestBuilders.post("/nwt/addEvent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(event)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Saved"));
    }
    @Test
    public void testAddNewEventUnSuccessfully() throws Exception {
        // Pripremamo JSON tijelo zahtjeva
        String requestJson = "{ \"name\": \"Test Event\", \"description\": \"Description\", \"date\": \"2024-03-30\", \"location\": \"Location\", \"doctorInfo\": { \"id\": 1, \"about\": \"About doctor\", \"user\": { \"id\": 1, \"email\": \"test@example.com\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"type\": 1, \"passwordHash\": \"passwordHash\" } } }";

        // Izvršavamo HTTP POST zahtjev s JSON tijelom
        mockMvc.perform(MockMvcRequestBuilders.post("/nwt/addEvent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                // Očekujemo da će status biti 400 Bad Request
                .andExpect(status().isBadRequest())
                // Očekujemo da će JSON odgovor sadržavati očekivanu poruku
                .andExpect(jsonPath("$.message").value("Datum mora biti u formatu 'DD.MM.YYYY'."));
    }
    @Test
    public void testGetAllEvents() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/nwt/allEvents"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetEvent_ValidId() throws Exception {
        // Kreiramo testnog korisnika
        UserEntity user = new UserEntity("test@example.com", "John", "Doe", 1, "passwordHash");

        // Kreiramo testnog liječnika povezanog s korisnikom
        DoctorInfoEntity doctor = new DoctorInfoEntity();
        doctor.setId(1L);
        doctor.setAbout("About doctor");
        doctor.setUser(user); // Povezujemo liječnika s korisnikom
        when(doctorInfoRepository.findById(1L)).thenReturn(doctor);
        // Kreiramo testni događaj
        EventEntity event = new EventEntity("Test Event", "Description", "03.04.2024", "Location");
        event.setId(1L);
        event.setDoctorInfo(doctor);
        // Postavljamo ponašanje mock-a za eventRepository
        when(eventRepository.findById(1L)).thenReturn(event);

        // Izvršavamo HTTP GET zahtjev za dohvat događaja s ID-om 1
        mockMvc.perform(MockMvcRequestBuilders.get("/nwt/events/1"))
                // Provjeravamo status odgovora da bude OK (200)
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Provjeravamo da je ID događaja ispravno postavljen
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                // Provjeravamo da je informacija o liječniku ispravno postavljena
                .andExpect(MockMvcResultMatchers.jsonPath("$.doctorInfo.user.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.doctorInfo.user.lastName").value("Doe"));
    }


    @Test
    public void testGetEvent_InvalidId() throws Exception {
        mockMvc.perform(get(String.format("/nwt/events/%d", -1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("not_found"))
                .andExpect(jsonPath("$.message").value("Not found event by id -1"));
    }

    @Test
    public void testGetReservationsForEvent_WithValidEventId() throws Exception {
        // Mock event
        EventEntity event = new EventEntity();
        event.setId(1L);

        // Mock reservations
        ReservationEntity reservation1 = new ReservationEntity();
        reservation1.setId(1L);
        ReservationEntity reservation2 = new ReservationEntity();
        reservation2.setId(2L);
        List<ReservationEntity> reservations = List.of(reservation1, reservation2);

        // Mock repository behavior
        when(eventRepository.findById(1)).thenReturn(event);
        when(reservationRepository.findByEvent(event)).thenReturn(reservations);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/nwt/reservations-for-events/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2));
    }

    @Test
    public void testGetReservationsForEvent_WithInvalidEventId() throws Exception {
        // Mock repository behavior for invalid event ID
        when(eventRepository.findById(100)).thenReturn(null);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/nwt/reservations-for-events/100"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

