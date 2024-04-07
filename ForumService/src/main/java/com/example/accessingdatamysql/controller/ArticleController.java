package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.exceptions.CategoryNotFoundException;
import com.example.accessingdatamysql.repository.*;

import com.example.accessingdatamysql.exceptions.ArticleNotFoundException;
import com.example.accessingdatamysql.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public @ResponseBody ResponseEntity<String> addNewArticle (@Valid @RequestBody ArticleEntity article) {
        ArticleEntity newArticle = articleService.addArticle(article);
        return ResponseEntity.ok("Article added.");
    }


    @GetMapping(path="/allArticles")
    public @ResponseBody Iterable<ArticleEntity> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping(path="/articles/{articleId}")
    public @ResponseBody ArticleEntity getArticle(@PathVariable long articleId) {
        ArticleEntity article = articleService.getArticleById(articleId);

        if (article == null) {
            throw new ArticleNotFoundException("Not found article by id: " + articleId);
        }

        return article;
    }

    @GetMapping(path="/articles/category/{category}")
    public @ResponseBody Iterable<ArticleEntity> getArticlesByCategory(@PathVariable String category) {
        return articleService.getByCategory(category);
    }

    @GetMapping(path="/articles/doctor/{doctorId}")
    public @ResponseBody Iterable<ArticleEntity> getArticleByDoctorId(@PathVariable long doctorId) {
        Iterable<ArticleEntity> article = articleService.getArticleByDoctorId(doctorId);

        if (article == null) {
            throw new ArticleNotFoundException(" Not found article by doctor id: " + doctorId);
        }

        return article;
    }

    // Kaskadno brisanje vidi poslije
    @DeleteMapping(path="/deleteArticle/{id}")
    public @ResponseBody ResponseEntity<String> deleteArticle (@PathVariable long id) {
        ArticleEntity article = this.articleService.getArticleById(id);
        if(article != null) {
            articleService.deleteArticle(id);
            return ResponseEntity.ok().build();
        }
        throw new ArticleNotFoundException("Not found article by id: " + id);
    }

}