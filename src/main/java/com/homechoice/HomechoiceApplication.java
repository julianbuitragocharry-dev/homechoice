package com.homechoice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomechoiceApplication {

    public static void main(String[] args) {
        Dotenv.configure()
                .systemProperties()
                .load();

        SpringApplication.run(HomechoiceApplication.class, args);
    }

}
