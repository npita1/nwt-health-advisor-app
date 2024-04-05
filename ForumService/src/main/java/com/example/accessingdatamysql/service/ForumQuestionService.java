package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.ArticleEntity;
import com.example.accessingdatamysql.entity.ForumAnswerEntity;
import com.example.accessingdatamysql.entity.ForumQuestionEntity;
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
