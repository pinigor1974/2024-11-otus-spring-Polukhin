package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@IntegrationComponentScan
@ComponentScan
@EnableIntegration
public class ApplicationIntegration {

    public static void main(String[] args) {
    	 SpringApplication.run(ApplicationIntegration.class, args);
    }
}
