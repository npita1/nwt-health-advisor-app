package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.repository.*;

import com.example.accessingdatamysql.exceptions.ArticleNotFoundException;
import com.example.accessingdatamysql.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
@RequestMapping(path="/nwt")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @PostMapping(path="/addArticle")
    public @ResponseBody String addNewArticle (@Valid @RequestBody ArticleEntity article) {
        articleService.addArticle(article);
        return "ArticleEntity Saved";
    }


    @GetMapping(path="/allArticles")
    public @ResponseBody Iterable<ArticleEntity> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping(path="/articles/{articleId}")
    public @ResponseBody ArticleEntity getArticle(@PathVariable long articleId) {
        ArticleEntity article = articleService.getArticleById(articleId);

        if (article == null) {
            // Da se vrati u JSON formatu objekat
            throw new ArticleNotFoundException(" Not found articleEntity by id: " + articleId);
        }

        return article;
    }

    @GetMapping(path="/articles/category/{category}")
    public @ResponseBody Iterable<ArticleEntity> getArticlesByCategory(@PathVariable String category) {
        return articleService.getByCategory(category);
    }

}