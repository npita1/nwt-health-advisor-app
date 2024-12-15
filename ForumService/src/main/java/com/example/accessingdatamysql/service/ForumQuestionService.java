package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.repository.ForumQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForumQuestionService {

    @Autowired
    private ForumQuestionRepository forumQuestionRepository;

    public ForumQuestionEntity addForumQuestion(ForumQuestionEntity forumQuestion) {
        return this.forumQuestionRepository.save(forumQuestion);
    }
    public ForumQuestionEntity addForumQuestionUser(ForumQuestionEntity forumQuestion, UserEntity user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User must be saved in the database before adding a forum question.");
        }
        // Validacija naslova pitanja
        if (forumQuestion.getTitle() == null || forumQuestion.getTitle().length() < 5 || forumQuestion.getTitle().length() > 100) {
            throw new IllegalArgumentException("Naslov mora biti između 5 i 100 znakova.");
        }

        // Validacija teksta pitanja
        if (forumQuestion.getText() == null || forumQuestion.getText().length() < 5 || forumQuestion.getText().length() > 1000) {
            throw new IllegalArgumentException("Tekst pitanja mora biti između 5 i 1000 znakova.");
        }

        // Validacija datuma (format 'DD.MM.YYYY')
        if (forumQuestion.getDate() == null || !forumQuestion.getDate().matches("^\\d{2}\\.\\d{2}\\.\\d{4}$")) {
            throw new IllegalArgumentException("Datum mora biti u formatu 'DD.MM.YYYY'.");
        }

        // Validacija anonimnosti
        if (forumQuestion.isAnonymity() != true && forumQuestion.isAnonymity() != false) {
            throw new IllegalArgumentException("Moraš odabrati da li pitanje bude anonimno ili ne.");
        }

        // Provjera korisnika (ako korisnik ne postoji u bazi)
        if (user == null) {
            throw new IllegalArgumentException("Korisnik nije pronađen.");
        }

        // Provjera kategorije (ako kategorija nije validna, može biti povezana s forumQuestion objektom)
        if (forumQuestion.getCategory().getId() == null) {
            throw new IllegalArgumentException("Kategorija mora biti postavljena.");
        }
        forumQuestion.setUser(user);
        return this.forumQuestionRepository.save(forumQuestion);
    }


    public Iterable<ForumQuestionEntity> getAllForumQuestions () {
        return this.forumQuestionRepository.findAll();
    }

    public ForumQuestionEntity getForumQuestionById (long id) {
        return this.forumQuestionRepository.findById(id);
    }
    public Iterable<ForumQuestionEntity> getForumQuestionsByUserId (long userId) {
        return this.forumQuestionRepository.findQuestionsByUserId(userId);
    }

    public Iterable<ForumQuestionEntity> getForumQuestionsByCategory (String category) {
        return this.forumQuestionRepository.findByCategory(category);
    }

}
