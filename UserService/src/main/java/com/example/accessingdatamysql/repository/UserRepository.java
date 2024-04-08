package com.example.accessingdatamysql.repository;

import java.util.List;

import com.example.accessingdatamysql.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.id =?1")
    UserEntity findById(long id);
    @Query("SELECT u FROM UserEntity u WHERE u.lastName =?1")
    List<UserEntity> findByLastName(String lastName);
    @Query("SELECT u FROM UserEntity u WHERE u.firstName =?1")
    List<UserEntity> findByFirstName(String firstName);


}