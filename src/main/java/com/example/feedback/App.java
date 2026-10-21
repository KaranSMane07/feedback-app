package com.example.feedback;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    // seed a default admin: admin / admin
    @Bean
    CommandLineRunner seed(UserRepo users) {
        return args -> {
            if (users.findByUsername("admin") == null) {
                users.save(new User("admin", "admin@example.com", "admin", "ADMIN"));
            }
        };
    }
}
