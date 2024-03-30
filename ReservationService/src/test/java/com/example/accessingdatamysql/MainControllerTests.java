/*
package com.example.accessingdatamysql;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers= MainController.class)
public class MainControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentRepository appointmentRepository;
   @MockBean
   private ReservationRepository reservationRepository;
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private DoctorInfoRepository doctorInfoRepository;

    @MockBean
    private EventRepository eventRepository;
    @Test
    public void testGetAllUsers() throws Exception {
        UserEntity user=new  UserEntity();
        user.setFirstName("Amaar");
        long id =1;
        user.setId(id);
        user.setEmail("amar123@gmail.com");
        user.setLastName("Omerović");
        user.setType(2);
        user.setPasswordHash("jikikskskskks12303030");
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/nwt/allUsers"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
    @Test
    public void testGetAppointmentsForUser() throws Exception{
        UserEntity user=new  UserEntity();
        user.setFirstName("Amaar");
        long id =1;
        user.setId(id);
        user.setEmail("amar123@gmail.com");
        user.setLastName("Omerović");
        user.setType(2);
        user.setPasswordHash("jikikskskskks12303030");
        DoctorInfoEntity doctorInfo= new DoctorInfoEntity();
        long id1 =1;
        doctorInfo.setId(id1);
        doctorInfo.setAbout("Magistra farmacije");
        doctorInfo.setUser(user);
        user.setDoctorInfo(doctorInfo);
        AppointmentEntity appointment=new AppointmentEntity();
        appointment.setId(id1);
        appointment.setUser(user);
        appointment.setDoctorInfo(doctorInfo);
        appointment.setDescription("Traumatologija");
        user.setAppointments(Arrays.asList(appointment));
        Mockito.when(appointmentRepository.findByUser(user)).thenReturn(Arrays.asList(appointment));
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/nwt/appointments-for-user/1"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect((ResultMatcher) content().json("[{\"id\": 1,\"description\": \"Traumatologija\"}]"));
    }
    }

 */
