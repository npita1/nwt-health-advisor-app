package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.ArticleEntity;
import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public ArticleEntity addArticle(ArticleEntity article) {
        return this.articleRepository.save(article);
    }

    public ArticleEntity addArticleDoctor(ArticleEntity article, DoctorInfoEntity doctor) {
        if (doctor.getId() == null) {
            throw new IllegalArgumentException("Doctor must be saved in the database before adding an article.");
        }

        article.setDoctor(doctor);
        return this.articleRepository.save(article);
    }


    public Iterable<ArticleEntity> getAllArticles () {
        return this.articleRepository.findAll();
    }

    public ArticleEntity getArticleById (long id) {
        return this.articleRepository.findById(id);
    }

    public void deleteArticle(long id) {
        this.articleRepository.deleteById(id);
    }

    public Iterable<ArticleEntity> getByCategory(String category) {
        return this.articleRepository.findByCategory(category);
    }


    public Iterable<ArticleEntity> getArticleByDoctorId(long doctorId) {
        return this.articleRepository.getArticlesByDoctorId(doctorId);
    }
}
