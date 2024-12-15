/*

package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.EventEntity;
import com.example.accessingdatamysql.entity.ReservationEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.repository.DoctorInfoRepository;
import com.example.accessingdatamysql.repository.EventRepository;
import com.example.accessingdatamysql.repository.ReservationRepository;
import com.example.accessingdatamysql.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReservationRepository reservationRepository;
    @MockBean
    private EventRepository eventRepository;
    @MockBean
    private DoctorInfoRepository doctorInfoRepository;
    @MockBean
    private UserRepository userRepository;
    @InjectMocks
    private ReservationController reservationController;
    private UserEntity user;
    private DoctorInfoEntity doctor;
    private EventEntity event;
    private ReservationEntity res1,res2;
     private void setup(){
        // Kreiramo testnog korisnika
         user = new UserEntity("test@example.com", "John", "Doe", 1, "passwordHash");
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(user);
        // Kreiramo testnog liječnika povezanog s korisnikom
        doctor = new DoctorInfoEntity();
        doctor.setId(1L);
        doctor.setAbout("About doctor");
        doctor.setUser(user); // Povezujemo liječnika s korisnikom
        when(doctorInfoRepository.findById(1L)).thenReturn(doctor);
        //kreiramo event
         event = new EventEntity("Test Event", "Description", "06.04.2024", "Location");
         event.setDoctorInfo(doctor);
         event.setId(1L);
        when(eventRepository.findById(1L)).thenReturn(event);
        res1=new ReservationEntity(2);
        res2 =new ReservationEntity(3);
        res1.setEvent(event);
        res2.setEvent(event);
    }
    @Test
    public void testAddNewReservationSuccessfully() throws Exception{
        setup();

        ReservationEntity reservation= new ReservationEntity(57);
        reservation.setEvent(event);
        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservation);

        mockMvc.perform(MockMvcRequestBuilders.post("/nwt/addReservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(reservation)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Saved"));
    }@Test
    public void testAddNewReservationUnSuccessfully() throws Exception{
       setup();
        ReservationEntity reservation= new ReservationEntity(57);
        //postavljamo event na null
        reservation.setEvent(null);
        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservation);
        mockMvc.perform(MockMvcRequestBuilders.post("/nwt/addReservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(reservation)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("validation"))
                // Očekujemo da će JSON odgovor sadržavati očekivanu poruku
                .andExpect(jsonPath("$.message").value("Event is mandatory"));
    }
    @Test
    public void testGetReservation_InvalidId() throws Exception {
        mockMvc.perform(get(String.format("/nwt/reservations/%d", -1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("not_found"))
                .andExpect(jsonPath("$.message").value("Not found reservation by id: -1"));
    }
    @Test
    public void testGetAllReservations() throws Exception {
         setup();
        // Kreiramo listu rezervacija koje će vratiti mock repository
        List<ReservationEntity> mockReservations = new ArrayList<>();
        mockReservations.add(res1);
        mockReservations.add(res2);
        // Postavljamo ponašanje mock repository-a
        when(reservationRepository.findAll()).thenReturn(mockReservations);

        // Izvršavamo zahtjev prema /allReservations endpointu
        mockMvc.perform(MockMvcRequestBuilders.get("/nwt/allReservations")
                        .contentType(MediaType.APPLICATION_JSON))
                // Očekujemo status kod 200 OK
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Očekujemo JSON odgovor koji sadrži listu rezervacija
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                // Provjeravamo svaku rezervaciju u listi
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numOfTicket", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].numOfTicket", Matchers.is(3)));
    }
    @Test
    public void testGetReservationById() throws Exception {
         setup();
        // Kreiramo rezervaciju za testiranje
        ReservationEntity reservation = new ReservationEntity(1);
        reservation.setId(1L);
        reservation.setEvent(event);
        when(reservationRepository.findById(1)).thenReturn(reservation);

        // Izvršavamo GET zahtjev za određenom rezervacijom po ID-u
        mockMvc.perform(MockMvcRequestBuilders.get("/nwt/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                // Očekujemo status kod 200 OK
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Očekujemo JSON odgovor koji sadrži ispravnu rezervaciju
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numOfTicket", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.event.name", Matchers.is("Test Event")));


    }
}

 */
