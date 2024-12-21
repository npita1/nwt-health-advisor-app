package com.example.accessingdatamysql.repository;

import java.util.List;
import java.util.Optional;

import com.example.accessingdatamysql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.id =?1")
    UserEntity findById(long id);
    @Query("SELECT u FROM UserEntity u WHERE u.lastName =?1")
    List<UserEntity> findByLastName(String lastName);
    @Query("SELECT u FROM UserEntity u WHERE u.firstName =?1")
    List<UserEntity> findByFirstName(String firstName);

    @Query("SELECT u FROM UserEntity u WHERE u.email =?1")
    UserEntity findByEmail1(String email);

    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    // Nova metoda za vraćanje svih korisnika koji nisu admin
    @Query("SELECT u FROM UserEntity u WHERE u.role <> 'ADMIN' OR u.role IS NULL")
    List<UserEntity> findAllNonAdminUsers();
}