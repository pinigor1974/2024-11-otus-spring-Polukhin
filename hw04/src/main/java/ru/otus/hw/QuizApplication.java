package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ConfigurationPropertiesScan
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class QuizApplication {
    public static void main(String[] args) {
        //Создать контекст Spring Boot приложения
        ApplicationContext context = SpringApplication.run(QuizApplication.class, args);
    }
}