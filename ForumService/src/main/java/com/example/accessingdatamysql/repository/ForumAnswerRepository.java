package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.ForumAnswer;
import org.springframework.data.repository.CrudRepository;

public interface ForumAnswerRepository extends CrudRepository<ForumAnswer, Long> {
}
