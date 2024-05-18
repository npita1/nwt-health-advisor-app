package com.example.accessingdatamysql;

import com.example.accessingdatamysql.auth.AuthenticationService;
import com.example.accessingdatamysql.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import static com.example.accessingdatamysql.entity.Role.ADMIN;
import static com.example.accessingdatamysql.entity.Role.USER;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class main {

	public static void main(String[] args) {
		SpringApplication.run(main.class, args);
	}

}

