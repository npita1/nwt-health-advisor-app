package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.repository.*;

import com.example.accessingdatamysql.exceptions.ArticleNotFoundException;
import com.example.accessingdatamysql.exceptions.ForumQuestionNotFoundException;
import jakarta.validation.Valid;
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
    public @ResponseBody ResponseEntity<String> addNewForumQuestion (@RequestBody ForumQuestion forumQuestion) {
        forumQuestionRepository.save(forumQuestion);
        String message = "{\"message\": \"Forum question saved\"}";
        // Vrati se JSON objekat pa kasnije kad nam bude trebao odgovor da se parsira
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping(path="/allForumQuestions")
    public @ResponseBody Iterable<ForumQuestion> getAllForumQuestions() {
        return forumQuestionRepository.findAll();
    }

    @GetMapping(path="/forumQuestions/{forumQuestionId}")
    public @ResponseBody ForumQuestion getForumQuestion(@PathVariable long forumQuestionId) {
        ForumQuestion forumQuestion = forumQuestionRepository.findById(forumQuestionId);

        if (forumQuestion == null) {
            throw new ForumQuestionNotFoundException(" Not found forum question by id: " + forumQuestionId);
        }

        return forumQuestion;
    }

}