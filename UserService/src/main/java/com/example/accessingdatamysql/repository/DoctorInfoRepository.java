package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DoctorInfoRepository extends CrudRepository<DoctorInfoEntity, Long> {
    DoctorInfoEntity findById(long id);

    @Query("SELECT u FROM DoctorInfoEntity u WHERE u.specialization =?1")
    List<DoctorInfoEntity> findBySpecialization(String specialization);

    @Query("SELECT u FROM DoctorInfoEntity u WHERE u.user.id =?1")
    DoctorInfoEntity getDoctorByUserId(Integer userId);
    void deleteByUser(UserEntity user);


    boolean existsByPhoneNumber(String phoneNumber);
}
