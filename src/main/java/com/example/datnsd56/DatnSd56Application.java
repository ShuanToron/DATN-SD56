package com.example.datnsd56;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
//@EnableWebSecurity
public class DatnSd56Application {

    public static void main(String[] args) {
        SpringApplication.run(DatnSd56Application.class, args);
    }

}
