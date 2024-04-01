package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.repository.*;

import com.example.accessingdatamysql.exceptions.ForumQuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
@RequestMapping(path="/demo")
public class ForumQuestionController {

    @Autowired
    private ForumQuestionRepository forumQuestionRepository;

    // ovdje imam fol vracanje jsona
    @PostMapping(path="/addForumQuestion")
    public @ResponseBody ResponseEntity<String> addNewForumQuestion (@RequestBody ForumQuestionEntity forumQuestionEntity) {
        forumQuestionRepository.save(forumQuestionEntity);
        String message = "{\"message\": \"Forum question saved\"}";
        // Vrati se JSON objekat pa kasnije kad nam bude trebao odgovor da se parsira
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping(path="/allForumQuestions")
    public @ResponseBody Iterable<ForumQuestionEntity> getAllForumQuestions() {
        return forumQuestionRepository.findAll();
    }

    @GetMapping(path="/questions/{forumQuestionId}")
    public @ResponseBody ForumQuestionEntity getForumQuestion(@PathVariable long forumQuestionId) {
        ForumQuestionEntity forumQuestionEntity = forumQuestionRepository.findById(forumQuestionId);

        if (forumQuestionEntity == null) {
            throw new ForumQuestionNotFoundException(" Not found forum question by id: " + forumQuestionId);
        }

        return forumQuestionEntity;
    }

    @GetMapping(path="/questions/user/{userId}")
    public @ResponseBody Iterable<ForumQuestionEntity> getForumQuestionsByUserId(@PathVariable Long userId) {
        return forumQuestionRepository.findQuestionsByUserId(userId);
    }

    @GetMapping(path="/questions/category/{category}")
    public @ResponseBody Iterable<ForumQuestionEntity> getForumQuestionsByCategory (@PathVariable String category) {
        return forumQuestionRepository.findByCategory(category);
    }
}