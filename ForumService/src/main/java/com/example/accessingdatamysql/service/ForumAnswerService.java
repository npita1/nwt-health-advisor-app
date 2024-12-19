package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.ArticleEntity;
import com.example.accessingdatamysql.entity.ForumAnswerEntity;
import com.example.accessingdatamysql.repository.ForumAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForumAnswerService {

    @Autowired
    private ForumAnswerRepository forumAnswerRepository;

    public ForumAnswerEntity addForumAnswer(ForumAnswerEntity forumAnswer) {
        // Validacija teksta pitanja
        if (forumAnswer.getText() == null || forumAnswer.getText().length() < 10 || forumAnswer.getText().length() > 255) {
            throw new IllegalArgumentException("Tekst odgovora mora biti između 10 i 255 znakova.");
        }

        // Validacija datuma (format 'DD.MM.YYYY')
        if (forumAnswer.getDate() == null || !forumAnswer.getDate().matches("^\\d{2}\\.\\d{2}\\.\\d{4}$")) {
            throw new IllegalArgumentException("Datum mora biti u formatu 'DD.MM.YYYY'.");
        }
        return this.forumAnswerRepository.save(forumAnswer);
    }

    public Iterable<ForumAnswerEntity> getAllForumAnswers () {
        return this.forumAnswerRepository.findAll();
    }

    public ForumAnswerEntity getForumAnswerById (long id) {
        return this.forumAnswerRepository.findById(id);
    }

    public void deleteForumAnswer(long id) {
        this.forumAnswerRepository.deleteById(id);
    }

    public Iterable<ForumAnswerEntity> getForumAnswersByQuestionId(Long questionId) {
        return this.forumAnswerRepository.getForumAnswersFromQuestionId(questionId);
    }

    public Iterable<ForumAnswerEntity> getForumAnswersByDoctorId(Long doctorId) {
        return this.forumAnswerRepository.getForumAnswersFromDoctorId(doctorId);
    }

    public void deleteForumAnswer(Long id) {
        ForumAnswerEntity answer = forumAnswerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Odgovor s ID-jem " + id + " nije pronađen."));
        forumAnswerRepository.delete(answer);
    }

}
