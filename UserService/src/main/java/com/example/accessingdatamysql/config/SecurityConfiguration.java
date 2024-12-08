package com.example.accessingdatamysql.config;

import lombok.RequiredArgsConstructor;
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

import static com.example.accessingdatamysql.entity.Role.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    public static final String[] openApiEndpoints = {
            "/webjars/**",
            "/v2/**",
            "/actuator/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/authentication/user",
            "/authentication/user-id",
            "/uploads/**"
    };



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/authentication/**").permitAll()
                        .requestMatchers(openApiEndpoints).permitAll()
                        .requestMatchers(GET,"/api/tokens/validate").permitAll()
                        .requestMatchers(GET,"/user/allDoctors").permitAll()
                        .requestMatchers(GET,"/user/doctors/specialist/{specialization}").permitAll()
                        .requestMatchers(POST,"/user/addNewDoctor").hasAnyRole(ADMIN.name())
                        .requestMatchers(GET,"/user/allUsers").hasRole(ADMIN.name())
                        .requestMatchers(GET,"/user/doctors/{doctorID}").hasAnyRole(ADMIN.name(),DOCTOR.name())
                        .requestMatchers(GET,"/user/doctor/getbyuserid").hasAnyRole(ADMIN.name(),DOCTOR.name(), USER.name())
                        .requestMatchers(GET,"/user/doctor/{doctorID}/articles").hasRole(DOCTOR.name())
                        .requestMatchers(GET,"/user/users/{userID}").hasAnyRole(ADMIN.name(),DOCTOR.name(),USER.name())
                        .requestMatchers(GET,"/user/users/firstname/{firstname}").hasAnyRole(ADMIN.name(),USER.name(),DOCTOR.name())
                        .requestMatchers(GET,"/user/users/lastname/{lastname}").hasAnyRole(ADMIN.name(),USER.name(),DOCTOR.name())
                        .requestMatchers(GET,"/user/users/email/{email}").hasAnyRole(ADMIN.name(),DOCTOR.name(),USER.name())
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/authentication/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }
}
