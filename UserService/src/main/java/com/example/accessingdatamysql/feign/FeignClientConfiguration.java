package com.example.accessingdatamysql.feign;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getCredentials() != null) {
                String token = authentication.getCredentials().toString();
                requestTemplate.header("Authorization", "Bearer " + token);
                // Log the token for debugging
                System.out.println("Added Authorization header with token: " + token);
            } else {
                System.out.println("Authentication or credentials are null, cannot add Authorization header.");
            }
        };
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; // Detaljan log nivo za debug
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder(); // Prilagođena obrada grešaka
    }
}