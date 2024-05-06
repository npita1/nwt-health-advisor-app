package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.feign.UserInterface;
import com.example.accessingdatamysql.repository.*;

import com.example.accessingdatamysql.service.ForumAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
@RequestMapping(path="/nwt")
public class ForumAnswerController {

    @Autowired
    private ForumAnswerService forumAnswerService;

    @Autowired
    UserInterface userClient;

    @Autowired
    private DoctorInfoRepository doctorInfoRepository;


    @PostMapping(path="/addForumAnswer")
    public @ResponseBody ResponseEntity<ForumAnswerEntity> addNewForumAnswer (@RequestBody ForumAnswerEntity forumAnswerEntity) {
        ForumAnswerEntity forumAnswer = forumAnswerService.addForumAnswer(forumAnswerEntity);
        return ResponseEntity.ok(forumAnswer);
    }

    @GetMapping(path="/allForumAnswers")
    public @ResponseBody Iterable<ForumAnswerEntity> getAllForumAnswers() {
        return forumAnswerService.getAllForumAnswers();
    }

    @GetMapping(path="/forumAnswers/question/{questionId}")
    public @ResponseBody Iterable<ForumAnswerEntity> getForumAnswersByQuestionId(@PathVariable Long questionId) {
        return forumAnswerService.getForumAnswersByQuestionId(questionId);
    }

    @GetMapping(path="/forumAnswers/doctor/{doctorId}")
    public @ResponseBody Iterable<ForumAnswerEntity> getAllForumAnswers(@PathVariable long doctorId) {
        DoctorInfoEntity userServiceDoctor = userClient.getDoctorID((int)doctorId);
        DoctorInfoEntity forumServiceDoctor = doctorInfoRepository.findByUserId(userServiceDoctor.getUser().getId());
        return forumAnswerService.getForumAnswersByDoctorId(forumServiceDoctor.getId());
    }

}