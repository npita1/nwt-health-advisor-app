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
