package com.example.refactoring_main;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class RefactoringMainApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        String clientId = dotenv.get("GOOGLE_CLIENT_ID");
        String clientSecret = dotenv.get("GOOGLE_CLIENT_SECRET");

        log.info("Google Client ID: " + clientId);
        log.info("Google Client Secret: " + clientSecret);

        SpringApplication.run(RefactoringMainApplication.class, args);
    }

}
