package com.example.accessingdatamysql;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    List<UserEntity> findByLastName(String lastName);

    UserEntity findById(long id);
}