package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.dto.ForumAnswerDTO;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.feign.UserInterface;
import com.example.accessingdatamysql.repository.*;

import com.example.accessingdatamysql.service.ForumAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Validated
@CrossOrigin
@RequestMapping(path="/forum")
public class ForumAnswerController {

    @Autowired
    private ForumAnswerService forumAnswerService;

    @Autowired
    UserInterface userClient;

    @Autowired
    private DoctorInfoRepository doctorInfoRepository;

    @Autowired
    private UserRepository userRepository;
    @PostMapping(path="/addForumAnswer")
    public @ResponseBody ResponseEntity<ForumAnswerEntity> addNewForumAnswer (@RequestBody ForumAnswerEntity forumAnswerEntity) {
        // Dohvatite doctor iz zahtjeva
        DoctorInfoEntity doctor1 = forumAnswerEntity.getDoctor();
        int did=doctor1.getId().intValue();
        DoctorInfoEntity doctor = userClient.getDoctorID(did);
        // Provjerite da li doctor postoji i modifikujte njegov ID
        if (doctor != null) {
            // Provjera da li je doktor vec spasen u bazi foruma
            DoctorInfoEntity forumDoctor = doctorInfoRepository.findByUserServiceId(doctor.getUser().getId());
            if (forumDoctor == null) {
                forumDoctor = new DoctorInfoEntity();
                UserEntity forumUser = new UserEntity();
                forumUser.setUserServiceId(doctor.getUser().getId());
                forumUser.setEmail(doctor.getUser().getEmail());
                forumUser.setFirstName(doctor.getUser().getFirstName());
                forumUser.setLastName(doctor.getUser().getLastName());
                forumUser.setPassword(doctor.getUser().getPassword());
                userRepository.save(forumUser);

                forumDoctor.setUser(forumUser);
                forumDoctor.setAbout(doctor.getAbout());
                forumDoctor.setSpecialization(doctor.getSpecialization());
                forumDoctor.setAvailability(doctor.getAvailability());
                forumDoctor.setPhoneNumber(doctor.getPhoneNumber());
                forumDoctor.setImagePath(doctor.getImagePath());
                doctorInfoRepository.save(forumDoctor);
            }
            doctor1.setId(forumDoctor.getId());
        }

        ForumAnswerEntity forumAnswer = forumAnswerService.addForumAnswer(forumAnswerEntity);
        return ResponseEntity.ok(forumAnswer);
    }

    @GetMapping(path="/allForumAnswers")
    public @ResponseBody Iterable<ForumAnswerEntity> getAllForumAnswers() {
        return forumAnswerService.getAllForumAnswers();
    }

    @GetMapping(path="/forumAnswers/question/{questionId}")
    public @ResponseBody List<ForumAnswerDTO> getForumAnswersByQuestionId(@PathVariable Long questionId) {
        return forumAnswerService.getForumAnswersByQuestionIdAsDTO(questionId);
    }

    @GetMapping(path="/forumAnswers/doctor/{doctorId}")
    public @ResponseBody Iterable<ForumAnswerEntity> getAllForumAnswers(@PathVariable long doctorId) {
        DoctorInfoEntity userServiceDoctor = userClient.getDoctorID((int)doctorId);
        DoctorInfoEntity forumServiceDoctor = doctorInfoRepository.findByUserId(userServiceDoctor.getUser().getId());
        return forumAnswerService.getForumAnswersByDoctorId(forumServiceDoctor.getId());
    }
    @DeleteMapping(path = "/deleteAnswer/{answerId}")
    public @ResponseBody ResponseEntity<Map<String, String>> deleteForumAnswer(@PathVariable Long answerId) {
        try {
            forumAnswerService.deleteForumAnswer(answerId);
            // Vraćamo odgovor u JSON formatu
            Map<String, String> response = Map.of("message", "Odgovor uspješno obrisan.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Vraćamo grešku u JSON formatu
            Map<String, String> errorResponse = Map.of("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

}