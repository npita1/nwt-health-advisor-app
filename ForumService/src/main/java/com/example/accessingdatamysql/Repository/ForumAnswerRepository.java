package com.example.accessingdatamysql.Repository;

import com.example.accessingdatamysql.Entity.ForumAnswer;
import com.example.accessingdatamysql.Entity.ForumQuestion;
import org.springframework.data.repository.CrudRepository;

public interface ForumAnswerRepository extends CrudRepository<ForumAnswer, Long> {
}
