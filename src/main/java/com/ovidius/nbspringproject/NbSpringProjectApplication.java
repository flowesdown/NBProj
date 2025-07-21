package com.ovidius.nbspringproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.ovidius.nbspringproject.controllers",
        "com.ovidius.nbspringproject.services",
        "com.ovidius.nbspringproject.repositories",
        "com.ovidius.nbspringproject.config",
        "com.ovidius.nbspringproject.filters"
})
public class NbSpringProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(NbSpringProjectApplication.class, args);
    }

}
