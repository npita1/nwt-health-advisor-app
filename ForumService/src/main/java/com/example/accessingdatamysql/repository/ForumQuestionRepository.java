package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.ForumQuestionEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ForumQuestionRepository extends JpaRepository<ForumQuestionEntity, Long> {

    ForumQuestionEntity findById(long id);
      void deleteAllByUser(UserEntity user);
      Iterable<ForumQuestionEntity> findAllByUser(UserEntity user);
    @Query("SELECT question FROM ForumQuestionEntity question WHERE question.user.id = :userId")
    Iterable<ForumQuestionEntity> findQuestionsByUserId(Long userId);

    @Query("SELECT question FROM ForumQuestionEntity question WHERE question.category.name = :category")
    List<ForumQuestionEntity> findByCategory(String category);
}
