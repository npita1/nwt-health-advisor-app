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
}
