package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.ForumAnswerEntity;
import com.example.accessingdatamysql.entity.ForumQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ForumAnswerRepository extends JpaRepository<ForumAnswerEntity, Long> {

    ForumAnswerEntity findById(long id);

    void deleteAllByQuestion(ForumQuestionEntity question);
    void deleteAllByDoctor(DoctorInfoEntity doctorInfo);
    @Query("SELECT fa FROM ForumAnswerEntity fa WHERE fa.question.id = :questionId")
    List<ForumAnswerEntity> getForumAnswersFromQuestionId(Long questionId);

    @Query("SELECT fa FROM ForumAnswerEntity fa WHERE fa.doctor.id = :doctorId")
    Iterable<ForumAnswerEntity> getForumAnswersFromDoctorId(Long doctorId);
}
