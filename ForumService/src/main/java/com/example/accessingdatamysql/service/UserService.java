package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.ForumQuestionEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    DoctorInfoRepository doctorInfoRepository;

    @Autowired
    ForumQuestionRepository forumQuestionRepository;

    @Autowired
    ForumAnswerRepository forumAnswerRepository;

    @Autowired
    ArticleRepository articleRepository;
    @Transactional
    public void deleteUser(long userServiceId) {
        //Pronađi usera kojeg treba obrisati
        UserEntity user = userRepository.findByUserServiceId(userServiceId);
        // Provjera ako korisnik nije pronađen
        if (user != null) {


            DoctorInfoEntity doctor = doctorInfoRepository.findByUserId(user.getId());

            if (doctor != null) {
                //Brisanje odgovora od doktora
                forumAnswerRepository.deleteAllByDoctor(doctor);

                articleRepository.deleteAllByDoctor(doctor);
                // Brisanje doktora
                doctorInfoRepository.delete(doctor);

            }
            Iterable<ForumQuestionEntity> forumQuestion = forumQuestionRepository.findAllByUser(user);
            for (ForumQuestionEntity question : forumQuestion) {
                forumAnswerRepository.deleteAllByQuestion(question);
            }
            forumQuestionRepository.deleteAllByUser(user);
            // Brisanje korisnika
            userRepository.delete(user);

        }
    }
}
