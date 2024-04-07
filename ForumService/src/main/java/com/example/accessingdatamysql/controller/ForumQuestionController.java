package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.repository.*;

import com.example.accessingdatamysql.exceptions.ForumQuestionNotFoundException;
import com.example.accessingdatamysql.service.ForumQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
@RequestMapping(path="/nwt")
public class ForumQuestionController {

    @Autowired
    private ForumQuestionService forumQuestionService;

    @PostMapping(path="/addForumQuestion")
    public @ResponseBody ResponseEntity<ForumQuestionEntity> addNewForumQuestion (@RequestBody ForumQuestionEntity forumQuestionEntity) {
        ForumQuestionEntity forumQuestion = forumQuestionService.addForumQuestion(forumQuestionEntity);
        return ResponseEntity.ok(forumQuestion);
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
    public @ResponseBody Iterable<ForumQuestionEntity> getForumQuestionsByUserId(@PathVariable Long userId) {
        // treba staviti nejlin user not found exception kad se uvezemo
        return forumQuestionService.getForumQuestionsByUserId(userId);
    }

    @GetMapping(path="/questions/category/{category}")
    public @ResponseBody Iterable<ForumQuestionEntity> getForumQuestionsByCategory (@PathVariable String category) {
        return forumQuestionService.getForumQuestionsByCategory(category);
    }

}