package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.ForumQuestion;
import org.springframework.data.repository.CrudRepository;

public interface ForumQuestionRepository extends CrudRepository<ForumQuestion, Long> {

    ForumQuestion findById(long id);
}
