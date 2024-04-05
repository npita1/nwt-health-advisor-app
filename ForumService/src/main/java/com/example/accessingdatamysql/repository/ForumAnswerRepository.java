package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.ForumAnswerEntity;
import org.springframework.data.repository.CrudRepository;

public interface ForumAnswerRepository extends CrudRepository<ForumAnswerEntity, Long> {

    ForumAnswerEntity findById(long id);

    void deleteById (long id);
}
