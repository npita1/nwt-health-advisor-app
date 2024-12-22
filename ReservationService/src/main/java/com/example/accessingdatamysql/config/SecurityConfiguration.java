package com.example.accessingdatamysql.config;


import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.apache.tomcat.jni.SSLConf.apply;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final AuthenticationFilter authenticationFilter;

    public static final String[] openApiEndpoints = {
            "/webjars/**",
            "/v2/**",
            "/actuator/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/reservation/allEvents"
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(openApiEndpoints).permitAll()
                        .requestMatchers(GET,"/reservation/allEvents").hasAnyRole("ADMIN","USER","DOCTOR")
                        .requestMatchers(POST,"/reservation/addEvent").hasRole("DOCTOR")
                        .requestMatchers(GET,"/reservation/events/{eventId}").hasRole("DOCTOR")
                        .requestMatchers(GET,"/reservation/reservations-for-events/{eventId}").hasRole("DOCTOR")
                        .requestMatchers(PUT,"/reservation/eventHeld/{eventId}").hasRole("DOCTOR")
                        .requestMatchers(POST,"/reservation/addReservation").hasAnyRole("USER","DOCTOR")
                        .requestMatchers(DELETE,"/reservation/deleteUser/{userServiceId}").hasRole("ADMIN")
                        .requestMatchers(DELETE,"/reservation/deleteReservation/{reservationId}").hasAnyRole("USER","DOCTOR")
                        .requestMatchers(DELETE,"/reservation/deleteEvent/{eventId}").hasRole("ADMIN")
                        .requestMatchers(GET,"/reservation/allReservations").hasAnyRole("ADMIN","DOCTOR")
                        .requestMatchers(GET,"/reservation/reservations/{reservationId}").hasAnyRole("USER","DOCTOR")
                        .requestMatchers(GET,"/reservation/reservations/for-user/{userId}").hasAnyRole("USER","DOCTOR")


                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);




        return http.build();
    }

}