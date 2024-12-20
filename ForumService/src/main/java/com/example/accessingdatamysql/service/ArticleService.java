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
        // Validacija naslova
        if (article.getTitle() == null || article.getTitle().length() < 5 || article.getTitle().length() > 150) {
            throw new IllegalArgumentException("Naslov mora biti između 5 i 150 znakova.");
        }

        // Validacija teksta
        if (article.getText() == null || article.getText().length() < 20 || article.getText().length() > 2500) {
            throw new IllegalArgumentException("Tekst članka mora biti između 20 i 2500 znakova.");
        }

        // Validacija datuma (format 'DD.MM.YYYY')
        if (article.getDate() == null || !article.getDate().matches("^\\d{2}\\.\\d{2}\\.\\d{4}$")) {
            throw new IllegalArgumentException("Datum mora biti u formatu 'DD.MM.YYYY'.");
        }

        if (article.getCategory().getId() == null) {
            throw new IllegalArgumentException("Kategorija mora biti postavljena.");
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



    public Iterable<ArticleEntity> getByCategory(String category) {
        return this.articleRepository.findByCategory(category);
    }


    public Iterable<ArticleEntity> getArticleByDoctorId(long doctorId) {
        return this.articleRepository.getArticlesByDoctorId(doctorId);
    }

    public void deleteArticle(Long id) {
        ArticleEntity article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Članak s ID-jem " + id + " nije pronađen."));
       articleRepository.delete(article);
    }
}
