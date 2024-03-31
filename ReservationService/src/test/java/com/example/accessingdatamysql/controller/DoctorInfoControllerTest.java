package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.entity.AppointmentEntity;
import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.UserEntity;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DoctorInfoController.class)
@ExtendWith(MockitoExtension.class)
public class DoctorInfoControllerTest {

    @MockBean
    private DoctorInfoRepository doctorInfoRepository;

    @MockBean
    private AppointmentRepository appointmentRepository;
    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private DoctorInfoController doctorInfoController;
    @Autowired
    private MockMvc mockMvc;
    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private UserEntity user,user1;
    private DoctorInfoEntity doctor;
    private AppointmentEntity app1,app2;
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
        app1=new AppointmentEntity("otorinolarniglogoija");
        app1.setDoctorInfo(doctor);
        app1.setId(1L);
        app2=new AppointmentEntity("traumatologija");
        app2.setDoctorInfo(doctor);
        app2.setId(2L);

    }
    private void setup2(){
        user = new UserEntity("test@example.com", "John", "Doe", 1, "passwordHash");
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(user);
        // Kreiramo testnog liječnika povezanog s korisnikom
        doctor = new DoctorInfoEntity();
        doctor.setId(1L);
        doctor.setAbout("About doctor");
        doctor.setUser(user); // Povezujemo liječnika s korisnikom
    }
    @Test
    public void testAddNewDoctor() throws Exception {
        setup2();
        when(doctorInfoRepository.save(any(DoctorInfoEntity.class))).thenReturn(doctor);
        mockMvc.perform(MockMvcRequestBuilders.post("/nwt/addDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new DoctorInfoEntity())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Saved"));
    }

    @Test
    public void testGetAllDoctors() throws Exception {
        List<DoctorInfoEntity> mockDoctors = new ArrayList<>();
        mockDoctors.add(doctor);
        when(doctorInfoRepository.findAll()).thenReturn(mockDoctors);
        mockMvc.perform(MockMvcRequestBuilders.get("/nwt/allDoctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));
    }

    @Test
    public void testGetAppointmentsForDoctor() throws Exception {
        DoctorInfoEntity doctor = new DoctorInfoEntity();
        doctor.setId(1L);
        when(doctorInfoRepository.findById(1)).thenReturn(doctor);
        when(appointmentRepository.findByDoctorInfo(doctor)).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/nwt/appointments-for-doctor/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }

    @Test
    public void testGetAppointmentsForDoctors1() throws Exception {
        setup();
        List<AppointmentEntity> mockAppointments = new ArrayList<>();
        mockAppointments.add(app1);
        mockAppointments.add(app2);
        when(appointmentRepository.findByDoctorInfo(doctor)).thenReturn(mockAppointments);
        mockMvc.perform(MockMvcRequestBuilders.get("/nwt/appointments-for-doctor/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].description").value("otorinolarniglogoija"))
                .andExpect(jsonPath("$[1].description").value("traumatologija"));
    }
    @Test
    public void testGetAppointmentsForDoctorsInvalidId() throws Exception {
        setup();
        List<AppointmentEntity> mockAppointments = new ArrayList<>();
        mockAppointments.add(app1);
        mockAppointments.add(app2);
        when(appointmentRepository.findByUser(user)).thenReturn(mockAppointments);
        mockMvc.perform(MockMvcRequestBuilders.get("/nwt/appointments-for-doctor/8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not found doctor by id 8"));;
    }

}
