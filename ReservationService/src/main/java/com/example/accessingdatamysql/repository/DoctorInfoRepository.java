package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DoctorInfoRepository extends JpaRepository<DoctorInfoEntity, Long> {
    DoctorInfoEntity findById(long id);

    DoctorInfoEntity findByUserId(long userId);
    @Query("SELECT a FROM DoctorInfoEntity a WHERE a.user.userServiceId = :userServiceId")
    DoctorInfoEntity findByUserServiceId(long userServiceId);
}
