package ru.otus.hw;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.dao.CsvQuestionDao;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestApplication.TestConfiguration.class})
public class TestApplication {

    @Autowired CsvQuestionDao csvQuestionDao;

    @Test
    void testQuestionDao(){

        int aaa = 1;
    }



    @Configuration
    public static class TestConfiguration{


        @Bean
        public CsvQuestionDao csvQuestionDao(){
            return new CsvQuestionDao(appProperties());
        }

        @Bean
        public AppProperties appProperties(){
            return new AppProperties("questions.csv", 3);
        }

    }
}
