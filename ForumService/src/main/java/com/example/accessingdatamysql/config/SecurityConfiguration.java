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
            "/swagger-ui.html"
    };

    public static final String[] openRoutes = {
            "/forum/allCategories",
            "/forum/allArticles",
            "/forum/allForumQuestions",
            "/forum/questions/category/{category}",
            "/uploads/**"

    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(openApiEndpoints).permitAll()
                        .requestMatchers(openRoutes).permitAll()
                        .requestMatchers(POST,"/forum/addForumQuestion").hasRole("USER")
                        .requestMatchers(POST,"/forum/addArticle").hasRole("DOCTOR")
                        .requestMatchers(POST,"/forum/addForumAnswer").hasRole("DOCTOR")
                        .requestMatchers(DELETE,"/forum/deleteUser").hasRole("ADMIN")
                        .requestMatchers(DELETE,"/forum/deleteAnswer/{answerId}").hasRole("ADMIN")
                        .requestMatchers(DELETE,"/forum/deleteQuestion/{questionId}").hasRole("ADMIN")
                        .requestMatchers(DELETE,"/forum/deleteArticle/{articleId}").hasRole("ADMIN")
                        .requestMatchers(GET,"/forum/forumAnswers/question/{questionId}").hasAnyRole("USER","DOCTOR","ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}