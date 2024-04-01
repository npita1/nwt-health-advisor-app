package com.example.accessingdatamysql.ControllerTests;
import com.example.accessingdatamysql.controller.ForumQuestionController;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.repository.DoctorInfoRepository;
import com.example.accessingdatamysql.repository.ForumQuestionRepository;
import com.example.accessingdatamysql.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ForumQuestionController.class)
public class ForumQuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ForumQuestionRepository forumQuestionRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private DoctorInfoRepository doctorInfoRepository;
    @InjectMocks
    private ForumQuestionController forumQuestionController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testAddNewForumQuestion() throws Exception {
        User user = new User("neki.email@mail.com", "ime", "prezime", 2, "passhash");
        CategoryEntity categoryEntity = new CategoryEntity("kategorija", "opis");
        ForumQuestionEntity forumQuestionEntity = new ForumQuestionEntity(user, categoryEntity, "naslovvv", "tekst pit", "22.03.2024", true);

        when(forumQuestionRepository.save(any(ForumQuestionEntity.class))).thenReturn(forumQuestionEntity);

        mockMvc.perform(post("/demo/addForumQuestion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(forumQuestionEntity)))
                .andExpect(status().isCreated()) // Updated to expect CREATED status
                .andExpect(content().string("{\"message\": \"Forum question saved\"}"));

        verify(forumQuestionRepository, times(1)).save(any(ForumQuestionEntity.class));
    }



    @Test
    public void testGetAllForumQuestions() throws Exception {
        User user = new User("neki.email@mail.com", "ime", "prezime", 2, "passhash");
        CategoryEntity categoryEntity = new CategoryEntity("kategorija", "opis");
        ForumQuestionEntity forumQuestionEntity = new ForumQuestionEntity(user, categoryEntity, "naslovvv", "tekst pit", "22.03.2024", true);

        User user2 = new User("neki.email@mail.com", "ime", "prezime", 2, "passhash");
        CategoryEntity categoryEntity2 = new CategoryEntity("kategorija", "opis");
        ForumQuestionEntity forumQuestionEntity2 = new ForumQuestionEntity(user2, categoryEntity2, "TITLE", "tekst pit", "22.03.2024", true);

        List<ForumQuestionEntity> forumQuestionEntities = Arrays.asList(forumQuestionEntity, forumQuestionEntity2);

        when(forumQuestionRepository.findAll()).thenReturn(forumQuestionEntities);

        mockMvc.perform(get("/demo/allForumQuestions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("naslovvv"))
                .andExpect(jsonPath("$[1].title").value("TITLE"));

        verify(forumQuestionRepository, times(1)).findAll();
    }
//    @Test
//    public void testGetAppointmentById_Success() throws Exception {
//        AppointmentEntity appointment = new AppointmentEntity("Test appointment");
//        appointment.setId(1L);
//
//        when(appointmentRepository.findById(anyLong())).thenReturn((appointment));
//
//        mockMvc.perform(get("/nwt/appointments/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.description").value("Test appointment"));
//
//        verify(appointmentRepository, times(1)).findById(1L);
//    }
//    @Test
//    public void getAppointmentUnSuccessfully1() throws Exception {
//        mockMvc.perform(get(String.format("/nwt/appointments/%d", -1))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.error").value("not_found"))
//                .andExpect(jsonPath("$.message").value("Not found appointment by id -1"));
//    }
//
//    @Test
//    void addAppointmentSuccessfully() throws Exception {
//        mockMvc.perform(post("/nwt/addAppointment")
//                        .content("\"description\":\"opis_neki\"")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//    @Test
//    void addAppointmentUnSuccessfully1() throws Exception {
//        mockMvc.perform(post("/nwt/addAppointment")
//                        .content("{\"description\": \"\"}")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("Description must not be blank"));
//    }
}
