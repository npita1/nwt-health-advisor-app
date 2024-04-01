package com.example.accessingdatamysql.Repository;

import com.example.accessingdatamysql.Entity.ForumQuestion;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ForumQuestionRepository extends CrudRepository<ForumQuestion, Long> {

    ForumQuestion findById(long id);
}
