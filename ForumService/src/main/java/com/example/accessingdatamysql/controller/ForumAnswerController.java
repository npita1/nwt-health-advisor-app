package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.repository.*;

import com.example.accessingdatamysql.service.ForumAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
@RequestMapping(path="/nwt")
public class ForumAnswerController {

    @Autowired
    private ForumAnswerService forumAnswerService;


    @PostMapping(path="/addForumAnswer")
    public @ResponseBody String addNewForumAnswer (@RequestBody ForumAnswerEntity forumAnswerEntity) {
        ForumAnswerEntity forumAnswer = forumAnswerService.addForumAnswer(forumAnswerEntity);
        return "Forum Answer Saved";
    }

    @GetMapping(path="/allForumAnswers")
    public @ResponseBody Iterable<ForumAnswerEntity> getAllForumAnswers() {
        return forumAnswerService.getAllForumAnswers();
    }

}