package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DoctorInfoRepository extends CrudRepository<DoctorInfoEntity, Long> {

    Optional<DoctorInfoEntity> findById(long id);

    @Query("SELECT a FROM DoctorInfoEntity a WHERE a.user.id = :id")
    DoctorInfoEntity findByUserId(long id);

    @Query("SELECT a FROM DoctorInfoEntity a WHERE a.user.userServiceId = :userServiceId")
    DoctorInfoEntity findByUserServiceId(long userServiceId);

}
