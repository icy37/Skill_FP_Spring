package com.example.skillfpspring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties
public class SkillFpSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkillFpSpringApplication.class, args);
    }
}
