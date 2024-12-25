package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.dto.ForumQuestionDTO;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.repository.ForumAnswerRepository;
import com.example.accessingdatamysql.repository.ForumQuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumQuestionService {

    @Autowired
    private ForumQuestionRepository forumQuestionRepository;

    @Autowired
    private ForumAnswerRepository forumAnswerRepository;
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
        if (forumQuestion.getText() == null || forumQuestion.getText().length() < 10 || forumQuestion.getText().length() > 400) {
            throw new IllegalArgumentException("Tekst pitanja mora biti između 10 i 400 znakova.");
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

    public List<ForumQuestionDTO> getForumQuestionsByCategory (String category) {
        List<ForumQuestionEntity> questions= forumQuestionRepository.findByCategory(category);
        return questions.stream().map(event -> {
            ForumQuestionDTO dto = new ForumQuestionDTO();
            dto.setId(event.getId());
            dto.setAnonymity(event.isAnonymity());
            dto.setTitle(event.getTitle());
            dto.setCategoryId(event.getCategory().getId());
            dto.setText(event.getText());
            dto.setDate(event.getDate());

            if (event.isAnonymity()) {
                dto.setUserFirstName("");
                dto.setUserLastName("");
            } else {
                dto.setUserFirstName(event.getUser().getFirstName());
                dto.setUserLastName(event.getUser().getLastName());
            }

            return dto;
        }).collect(Collectors.toList());
    }
    @Transactional
    public void deleteForumQuestion(Long questionId) {
        // Pronađi pitanje koje treba obrisati
        ForumQuestionEntity question = forumQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Pitanje s ID-om " + questionId + " ne postoji."));

        // Brisanje svih odgovora povezanih s pitanjem
        forumAnswerRepository.deleteAllByQuestion(question);

        // Brisanje pitanja
        forumQuestionRepository.delete(question);
    }
    public List<ForumQuestionDTO> getAllForumQuestionsAsDTO() {
        List<ForumQuestionEntity> questions=forumQuestionRepository.findAll();
        return questions.stream().map(event -> {
            ForumQuestionDTO dto = new ForumQuestionDTO();
            dto.setId(event.getId());
            dto.setAnonymity(event.isAnonymity());
            dto.setTitle(event.getTitle());
            dto.setCategoryId(event.getCategory().getId());
            dto.setText(event.getText());
            dto.setDate(event.getDate());

            if (event.isAnonymity()) {
                dto.setUserFirstName("");
                dto.setUserLastName("");
            } else {
                dto.setUserFirstName(event.getUser().getFirstName());
                dto.setUserLastName(event.getUser().getLastName());
            }

            return dto;
        }).collect(Collectors.toList());
    }
}
