package com.example.skillsproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class SkilltrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkilltrackerApplication.class, args);
    }

}
