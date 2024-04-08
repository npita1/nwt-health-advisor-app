package com.example.accessingdatamysql.repository;

import java.util.List;

import com.example.accessingdatamysql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByLastName(String lastName);

    UserEntity findById(long id);
}