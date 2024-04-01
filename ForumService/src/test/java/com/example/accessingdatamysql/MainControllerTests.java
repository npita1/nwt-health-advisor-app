//package com.example.accessingdatamysql;
//
//import com.example.accessingdatamysql.controller.MainController;
//import com.example.accessingdatamysql.entity.User;
//import com.example.accessingdatamysql.repository.*;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.Arrays;
//
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//@WebMvcTest(controllers= MainController.class)
//public class MainControllerTests {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private UserRepository userRepository;
//
//    @MockBean
//    private ArticleRepository articleRepository;
//
//    @MockBean
//    private CategoryRepository categoryRepository;
//
//    @MockBean
//    private DoctorInfoRepository doctorInfoRepository;
//
//    @MockBean
//    private ForumAnswerRepository forumAnswerRepository;
//
//    @MockBean
//    private ForumQuestionRepository forumQuestionRepository;
//
//    @Test
//    public void testGetAllUsers() throws Exception {
//        User user=new  User();
//        user.setFirstName("Amaar");
//        long id =1;
//        user.setId(id);
//        user.setEmail("amar123@gmail.com");
//        user.setLastName("Omerović");
//        user.setType(2);
//        user.setPasswordHash("jikikskskskks12303030");
//        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user));
//        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/demo/allUsers"))
//                .andExpect(MockMvcResultMatchers.status().is(200));
//    }
//
//}