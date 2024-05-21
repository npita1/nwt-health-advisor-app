package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public boolean isTokenValid(String token) {
        var storedToken = tokenRepository.findByToken(token).orElse(null);
        return storedToken != null && !storedToken.isExpired() && !storedToken.isRevoked();
    }

    public void invalidateToken(String token) {
        var storedToken = tokenRepository.findByToken(token).orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }
    }
}