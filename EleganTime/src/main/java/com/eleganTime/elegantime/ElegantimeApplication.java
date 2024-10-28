package com.eleganTime.elegantime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ElegantimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElegantimeApplication.class, args);
    }
}