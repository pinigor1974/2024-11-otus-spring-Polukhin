package ru.otus.test.hw;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.service.TestServiceImpl;

@Configuration
public  class TestConfiguration{


    @Bean
    public CsvQuestionDao csvQuestionDao(){
        return new CsvQuestionDao(appProperties());
    }

    @Bean
    public AppProperties appProperties(){
        return new AppProperties("questions.csv", 3);
    }

    @Bean
    public TestServiceImpl testServiceImpl() {return Mockito.mock(TestServiceImpl.class);}
}