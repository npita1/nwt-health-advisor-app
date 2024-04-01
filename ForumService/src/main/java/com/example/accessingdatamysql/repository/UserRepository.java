package com.example.accessingdatamysql.repository;
import com.example.accessingdatamysql.entity.User;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByLastName(String lastName);

    User findById(long id);

}