package com.example.accessingdatamysql.repository;
import com.example.accessingdatamysql.entity.UserEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByLastName(String lastName);

    UserEntity findById(long id);

    @Query("SELECT a FROM UserEntity a WHERE a.userServiceId = :userServiceId")
    UserEntity findByUserServiceId(long userServiceId);

}