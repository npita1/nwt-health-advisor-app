package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.feign.UserInterface;
import com.example.accessingdatamysql.repository.*;

import com.example.accessingdatamysql.exceptions.ForumQuestionNotFoundException;
import com.example.accessingdatamysql.service.ForumQuestionService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@Validated
@CrossOrigin
@RequestMapping(path="/forum")
public class ForumQuestionController {

    @Autowired
    private ForumQuestionService forumQuestionService;

    @Autowired
    UserInterface userClient;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private DoctorInfoRepository doctorInfoRepository;

    @PostMapping(path="/addForumQuestionClassic")
    public @ResponseBody ResponseEntity<ForumQuestionEntity> addNewForumQuestion (@RequestBody ForumQuestionEntity forumQuestionEntity) {
        ForumQuestionEntity forumQuestion = forumQuestionService.addForumQuestion(forumQuestionEntity);
        return ResponseEntity.ok(forumQuestion);
    }

    @PostMapping(path="/addForumQuestion")
    public @ResponseBody ResponseEntity<?> addForumQuestion (@RequestParam("userId") int userId,  @Valid @RequestBody ForumQuestionEntity forumQuestion
                                                             ) {


        UserEntity user = userClient.getUserByID(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Korisnik s ID-om " + user + " nije pronađen.");
        }

        // Provjeri je li user već spremljen u bazi
        UserEntity forumUser = userRepository.findByUserServiceId(userId);
        if (forumUser == null) {
            forumUser = new UserEntity();
            forumUser.setUserServiceId(user.getId());
            forumUser.setEmail(user.getEmail());
            forumUser.setFirstName(user.getFirstName());
            forumUser.setLastName(user.getLastName());
            forumUser.setPassword(user.getPassword());
            userRepository.save(forumUser);
            DoctorInfoEntity doctor = userClient.getDoctorByUserId1(userId);
            if(doctor!=null){
                DoctorInfoEntity forumDoctor = new DoctorInfoEntity();
                forumDoctor.setAbout(doctor.getAbout());
                forumDoctor.setSpecialization(doctor.getSpecialization());
                forumDoctor.setPhoneNumber(doctor.getPhoneNumber());
                forumDoctor.setAvailability(doctor.getAvailability());
                forumDoctor.setUser(forumUser);
                forumDoctor.setImagePath(doctor.getImagePath());
                doctorInfoRepository.save(forumDoctor);
            }
        }

        ForumQuestionEntity savedForumQuestion = forumQuestionService.addForumQuestionUser(forumQuestion, forumUser);

        return ResponseEntity.ok(savedForumQuestion);
    }

    @GetMapping(path="/allForumQuestions")
    public @ResponseBody Iterable<ForumQuestionEntity> getAllForumQuestions() {
        return forumQuestionService.getAllForumQuestions();
    }

    @GetMapping(path="/questions/{forumQuestionId}")
    public @ResponseBody ForumQuestionEntity getForumQuestion(@PathVariable long forumQuestionId) {
        ForumQuestionEntity forumQuestionEntity = forumQuestionService.getForumQuestionById(forumQuestionId);

        if (forumQuestionEntity == null) {
            throw new ForumQuestionNotFoundException(" Not found forum question by id: " + forumQuestionId);
        }

        return forumQuestionEntity;
    }

    @GetMapping(path="/questions/user/{userId}")
    public @ResponseBody Iterable<ForumQuestionEntity> getForumQuestionsByUserId(@PathVariable long userId) {
        UserEntity forumServiceUser = userRepository.findByUserServiceId(userId);
        return forumQuestionService.getForumQuestionsByUserId(forumServiceUser.getId());
    }

    @GetMapping(path="/questions/category/{category}")
    public @ResponseBody Iterable<ForumQuestionEntity> getForumQuestionsByCategory (@PathVariable String category) {
        return forumQuestionService.getForumQuestionsByCategory(category);
    }

    @DeleteMapping(path = "/deleteQuestion/{questionId}")
    public @ResponseBody ResponseEntity<Map<String, String>> deleteForumQuestion(@PathVariable Long questionId) {
        try {
            forumQuestionService.deleteForumQuestion(questionId);
            // Vraćamo odgovor u JSON formatu
            Map<String, String> response = Map.of("message", "Pitanje i svi povezani odgovori su uspješno obrisani.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Vraćamo grešku u JSON formatu
            Map<String, String> errorResponse = Map.of("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }


}