package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import ru.otus.hw.service.TestRunnerService;

@ConfigurationPropertiesScan
@SpringBootApplication
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {

        //Создать контекст Spring Boot приложения
        ApplicationContext context = SpringApplication.run(Application.class, args);;
        var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();

    }
}