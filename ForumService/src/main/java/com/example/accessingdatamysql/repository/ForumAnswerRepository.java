package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.ForumAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ForumAnswerRepository extends JpaRepository<ForumAnswerEntity, Long> {

    ForumAnswerEntity findById(long id);

    void deleteById (long id);

    @Query("SELECT fa FROM ForumAnswerEntity fa WHERE fa.question.id = :questionId")
    Iterable<ForumAnswerEntity> getForumAnswersFromQuestionId(Long questionId);

    @Query("SELECT fa FROM ForumAnswerEntity fa WHERE fa.doctor.id = :doctorId")
    Iterable<ForumAnswerEntity> getForumAnswersFromDoctorId(Long doctorId);
}
