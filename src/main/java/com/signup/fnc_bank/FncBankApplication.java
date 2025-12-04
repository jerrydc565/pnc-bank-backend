package com.signup.fnc_bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FncBankApplication {

        public static void main(String[] args) {
                SpringApplication.run(FncBankApplication.class, args);
                System.out.println("\n======================================");
                System.out.println("Spring Boot Application Started!");
                System.out.println("API Available at: http://localhost:8080");
                System.out.println("Test endpoint: http://localhost:8080/api/test");
                System.out.println("======================================\n");
        }

}
