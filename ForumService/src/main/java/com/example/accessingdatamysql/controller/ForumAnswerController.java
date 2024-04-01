package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
@RequestMapping(path="/demo")
public class ForumAnswerController {

    @Autowired
    private ForumAnswerRepository forumAnswerRepository;


    @PostMapping(path="/addForumAnswer")
    public @ResponseBody String addNewForumAnswer (@RequestBody ForumAnswerEntity forumAnswerEntity) {
        forumAnswerRepository.save(forumAnswerEntity);
        return "Forum Answer Saved";
    }

    @GetMapping(path="/allForumAnswers")
    public @ResponseBody Iterable<ForumAnswerEntity> getAllForumQuestions() {
        return forumAnswerRepository.findAll();
    }

}