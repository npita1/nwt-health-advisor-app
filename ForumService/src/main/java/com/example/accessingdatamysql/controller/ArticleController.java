package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.exceptions.CategoryNotFoundException;
import com.example.accessingdatamysql.feign.UserInterface;
import com.example.accessingdatamysql.repository.*;

import com.example.accessingdatamysql.exceptions.ArticleNotFoundException;
import com.example.accessingdatamysql.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@Validated
@RequestMapping(path="/nwt")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    UserInterface userClient;

    @Autowired
    private DoctorInfoRepository doctorInfoRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @PostMapping(path="/addArticle")
    public @ResponseBody ResponseEntity<String> addNewArticle (@Valid @RequestBody ArticleEntity article) {
        ArticleEntity newArticle = articleService.addArticle(article);
        return ResponseEntity.ok("Article added.");
    }

//    @PostMapping(path="/addArticle")
//    public @ResponseBody ResponseEntity<String> addArticleWithDoctor (
//            @RequestParam("doctorId") int doctorId,
//            @RequestBody ArticleEntity article) {
//        DoctorInfoEntity doctor = userClient.getDoctorID(doctorId);
//
//        DoctorInfoEntity doctor1 = new DoctorInfoEntity();
//        doctor1.setId(doctor.getId());
//        doctor1.setUser(doctor.getUser());
//        doctor1.setAbout(doctor.getAbout());
//        doctor1.setSpecialization(doctor.getSpecialization());
//        doctorInfoRepository.save(doctor1);
//
//        article.setDoctor(doctor1);
//
//        ArticleEntity newArticle = articleService.addArticle(article);
//        return ResponseEntity.ok("Article added.");
//    }


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
    @GetMapping(path="/userCom/articles/doctor/{doctorId}")
    public @ResponseBody Map<String, String> getTitleAndTextArticleDoctorId(@PathVariable long doctorId) {
        Iterable<ArticleEntity> articles = articleService.getArticleByDoctorId(doctorId);

        if (articles == null) {
            throw new ArticleNotFoundException("Not found articles by doctor id: " + doctorId);
        }

        Map<String, String> articleMap = new HashMap<>();
        for (ArticleEntity article : articles) {
            articleMap.put(article.getTitle(), article.getText());
        }

        return articleMap;
    }
    @PostMapping(path="/addArticle1")
    public @ResponseBody ResponseEntity<String> addArticle1(@RequestParam("doctorId") Long doctorId,
                                              @RequestParam("categoryId") Long categoryId,
                                              @RequestParam("title") String title,
                                              @RequestParam("text") String text,
                                              @RequestParam("date") String date) {
        DoctorInfoEntity doctor = doctorInfoRepository.findById(doctorId)
                .orElseThrow(() -> new ArticleNotFoundException("Not found doctor by id: " + doctorId));
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Not found category by id: "+ categoryId));

        ArticleEntity article = new ArticleEntity();
        article.setDoctor(doctor);
        article.setCategory(category);
        article.setTitle(title);
        article.setText(text);
        article.setDate(date);

        articleRepository.save(article);
        return ResponseEntity.ok("Article successfully added");
    }

}