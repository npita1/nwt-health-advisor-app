//package com.example.accessingdatamysql.ControllerTests;
//import com.example.accessingdatamysql.controller.ArticleController;
//import com.example.accessingdatamysql.entity.ArticleEntity;
//import com.example.accessingdatamysql.entity.CategoryEntity;
//import com.example.accessingdatamysql.entity.DoctorInfoEntity;
//import com.example.accessingdatamysql.entity.UserEntity;
//import com.example.accessingdatamysql.repository.ArticleRepository;
//import com.example.accessingdatamysql.repository.DoctorInfoRepository;
//import com.example.accessingdatamysql.repository.UserRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(ArticleController.class)
//public class ArticleControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ArticleRepository articleRepository;
//    @MockBean
//    private UserRepository userRepository;
//    @MockBean
//    private DoctorInfoRepository doctorInfoRepository;
//    @InjectMocks
//    private ArticleController articleController;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Test
//    public void testAddNewArticle() throws Exception {
//        UserEntity user = new UserEntity("neki.email@mail.com", "ime", "prezime", 2, "passhash");
//        DoctorInfoEntity doctor = new DoctorInfoEntity(user, "reference");
//        CategoryEntity categoryEntity = new CategoryEntity("kategorija", "opis");
//        ArticleEntity articleEntity = new ArticleEntity(doctor, categoryEntity, "teks", "22.01.2024", "naslov");
//
//
//        when(articleRepository.save(any(ArticleEntity.class))).thenReturn(articleEntity);
//
//        mockMvc.perform(post("/demo/addArticle")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(articleEntity)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("ArticleEntity Saved"));
//
//        verify(articleRepository, times(1)).save(any(ArticleEntity.class));
//    }
//
//
//    @Test
//    public void testGetAllAppointments() throws Exception {
//        UserEntity user = new UserEntity("neki.email@mail.com", "ime", "prezime", 2, "passhash");
//        DoctorInfoEntity doctor = new DoctorInfoEntity(user, "reference");
//        CategoryEntity categoryEntity = new CategoryEntity("kategorija", "opis");
//        ArticleEntity articleEntity = new ArticleEntity(doctor, categoryEntity, "teks", "22.01.2024", "naslov");
//
//        UserEntity user2 = new UserEntity("neki.email@mail.com", "ime", "prezime", 2, "passhash");
//        DoctorInfoEntity doctor2 = new DoctorInfoEntity(user2, "reference");
//        CategoryEntity categoryEntity2 = new CategoryEntity("kategorija", "opis");
//        ArticleEntity articleEntity2 = new ArticleEntity(doctor2, categoryEntity2, "teks", "22.01.2024", "naslov2");
//
//        List<ArticleEntity> articleEntities = Arrays.asList(articleEntity, articleEntity2);
//
//        when(articleRepository.findAll()).thenReturn(articleEntities);
//
//        mockMvc.perform(get("/demo/allArticles"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0].title").value("naslov"))
//                .andExpect(jsonPath("$[1].title").value("naslov2"));
//
//        verify(articleRepository, times(1)).findAll();
//    }
////    @Test
////    public void testGetAppointmentById_Success() throws Exception {
////        AppointmentEntity appointment = new AppointmentEntity("Test appointment");
////        appointment.setId(1L);
////
////        when(appointmentRepository.findById(anyLong())).thenReturn((appointment));
////
////        mockMvc.perform(get("/nwt/appointments/1"))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.id").value(1))
////                .andExpect(jsonPath("$.description").value("Test appointment"));
////
////        verify(appointmentRepository, times(1)).findById(1L);
////    }
////    @Test
////    public void getAppointmentUnSuccessfully1() throws Exception {
////        mockMvc.perform(get(String.format("/nwt/appointments/%d", -1))
////                        .contentType(MediaType.APPLICATION_JSON))
////                .andExpect(status().isNotFound())
////                .andExpect(jsonPath("$.error").value("not_found"))
////                .andExpect(jsonPath("$.message").value("Not found appointment by id -1"));
////    }
////
////    @Test
////    void addAppointmentSuccessfully() throws Exception {
////        mockMvc.perform(post("/nwt/addAppointment")
////                        .content("\"description\":\"opis_neki\"")
////                        .contentType(MediaType.APPLICATION_JSON))
////                .andExpect(status().isOk());
////    }
////    @Test
////    void addAppointmentUnSuccessfully1() throws Exception {
////        mockMvc.perform(post("/nwt/addAppointment")
////                        .content("{\"description\": \"\"}")
////                        .contentType(MediaType.APPLICATION_JSON))
////                .andExpect(status().isBadRequest())
////                .andExpect(jsonPath("$.message").value("Description must not be blank"));
////    }
//}
