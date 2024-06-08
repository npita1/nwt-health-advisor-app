package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("SELECT t FROM Token t WHERE t.user.id = :userId AND (t.expired = false OR t.revoked = false)")
    List<Token> findAllValidTokenByUser(Long userId);

    Optional<Token> findByToken(String token);

    @Query("SELECT t.user.id FROM Token t WHERE t.token = :token AND t.expired = false AND t.revoked = false")
    Long findUserIdByToken(String token);

    @Modifying
    @Query("DELETE FROM Token t WHERE t.user.id = :userId")
    void deleteByUserId(Long userId);
}