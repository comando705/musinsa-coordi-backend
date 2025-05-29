package com.musinsacoordi.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MusinsaCoordiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusinsaCoordiBackendApplication.class, args);
    }

}
