package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();

        System.setProperty(
                "MONGO_URI",
                dotenv.get("MONGO_URI")
        );

        SpringApplication.run(Application.class, args);
    }
}