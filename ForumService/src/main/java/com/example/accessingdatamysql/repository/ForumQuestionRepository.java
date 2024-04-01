package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.ForumQuestionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ForumQuestionRepository extends CrudRepository<ForumQuestionEntity, Long> {

    ForumQuestionEntity findById(long id);

    @Query("SELECT question FROM ForumQuestionEntity question WHERE question.user.id = :userId")
    Iterable<ForumQuestionEntity> findQuestionsByUserId(Long userId);
}
