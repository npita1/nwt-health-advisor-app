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
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

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


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(openApiEndpoints).permitAll()
                        .requestMatchers(GET,"reservation/allAppointments").hasAnyRole("ADMIN","DOCTOR")
                        .requestMatchers(GET, "reservation/appointments/{appointmentId}").hasAnyRole("ADMIN","DOCTOR")
                        .requestMatchers(GET,"reservation/appointments/doctor/{doctorName}").hasAnyRole("ADMIN","DOCTOR")
                        .requestMatchers(GET,"reservation/appointments/user/{userName}/description/{description}").hasRole("USER")
                        .requestMatchers(GET,"reservation//appointments/user/{userId}").hasRole("USER")
                        .requestMatchers(POST,"reservation/addAppointment").hasAnyRole("DOCTOR","USER")
                        .requestMatchers(GET,"/reservation/allEvents").hasAnyRole("ADMIN","USER","DOCTOR")
                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);




        return http.build();
    }

}