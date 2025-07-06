package ru.otus.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.services.ConsoleIOService;


@Configuration
public class AppConfig {

	@Bean
    public ConsoleIOService consoleIOService(){
        return new ConsoleIOService(System.in,System.out);
    }


}
