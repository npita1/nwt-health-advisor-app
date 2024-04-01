package com.example.accessingdatamysql.Repository;
import com.example.accessingdatamysql.Entity.User;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByLastName(String lastName);

    User findById(long id);

}