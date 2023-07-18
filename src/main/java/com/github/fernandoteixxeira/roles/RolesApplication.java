package com.github.fernandoteixxeira.roles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class RolesApplication {

    public static void main(String[] args) {
        SpringApplication.run(RolesApplication.class, args);
    }

}
