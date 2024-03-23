package com.example.accessingdatamysql.Repository;

import com.example.accessingdatamysql.Entity.ForumQuestion;
import org.springframework.data.repository.CrudRepository;

public interface ForumQuestionRepository extends CrudRepository<ForumQuestion, Long> {
}
