package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.repository.*;

import com.example.accessingdatamysql.exceptions.ArticleNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public @ResponseBody String addNewArticle (@Valid @RequestBody ArticleEntity articleEntity) {
        articleRepository.save(articleEntity);
        return "ArticleEntity Saved";
    }


    @GetMapping(path="/allArticles")
    public @ResponseBody Iterable<ArticleEntity> getAllArticles() {
        return articleRepository.findAll();
    }

    @GetMapping(path="/articles/{articleId}")
    public @ResponseBody ArticleEntity getArticle(@PathVariable long articleId) {
        ArticleEntity articleEntity = articleRepository.findById(articleId);

        if (articleEntity == null) {
            // Da se vrati u JSON formatu objekat
            throw new ArticleNotFoundException(" Not found articleEntity by id: " + articleId);
        }

        return articleEntity;
    }

    @GetMapping(path="/articlesByCategory/{category}")
    public @ResponseBody Iterable<ArticleEntity> getArticlesByCategory(@PathVariable String category) {
        return articleRepository.findByCategory(category);
    }

}