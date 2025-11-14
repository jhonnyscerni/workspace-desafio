package com.municipality.servants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ServantsManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServantsManagementApplication.class, args);
    }
}
