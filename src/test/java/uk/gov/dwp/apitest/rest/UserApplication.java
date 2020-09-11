package uk.gov.dwp.apitest.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"uk.gov.dwp.apitest"})
class UserApplication {
    static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}