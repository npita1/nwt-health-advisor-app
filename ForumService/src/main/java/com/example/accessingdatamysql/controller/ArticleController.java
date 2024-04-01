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
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;


    @PostMapping(path="/addArticle")
    public @ResponseBody String addNewArticle (@Valid @RequestBody Article article) {
        articleRepository.save(article);
        return "Article Saved";
    }


    @GetMapping(path="/allArticles")
    public @ResponseBody Iterable<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @GetMapping(path="/articles/{articleId}")
    public @ResponseBody Article getArticle(@PathVariable long articleId) {
        Article article = articleRepository.findById(articleId);

        if (article == null) {
            // Da se vrati u JSON formatu objekat
            throw new ArticleNotFoundException(" Not found article by id: " + articleId);
        }

        return article;
    }


}