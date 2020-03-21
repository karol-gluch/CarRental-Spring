package com.car.rental.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.car.rental.project.repository")
@EntityScan("com.car.rental.project.model")
public class CarRentalSpringApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalSpringApplication.class, args);
    }

}
