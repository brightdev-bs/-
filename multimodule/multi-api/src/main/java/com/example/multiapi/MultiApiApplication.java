package com.example.multiapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {"com.example.multiapi", "com.example.multicommon"}
)
public class MultiApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiApiApplication.class, args);
    }

}
