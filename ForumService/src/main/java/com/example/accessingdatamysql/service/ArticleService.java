package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.ArticleEntity;
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


}
