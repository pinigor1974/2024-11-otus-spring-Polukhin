package ru.otus.test.hw;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.dao.CsvQuestionDao;
import org.junit.jupiter.api.Assertions;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.TestServiceImpl;

import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestApplication.TestConfiguration.class})
public class TestApplication {

    @Autowired  CsvQuestionDao csvQuestionDao;
    @Autowired TestServiceImpl testServiceImpl;

    @Test
    void testQuestionDao(){
        var questions = csvQuestionDao.findAll();
        Assertions.assertEquals(questions.size(), 3);
    }

    @Test
    void testServiceWithMockito() {
        var student = new Student("Igor", "Polukhin");
        var testResult = new TestResult(student);
        testResult.applyAnswer(new Question("Some question", new ArrayList<>()), true);
        Mockito.when(testServiceImpl.executeTestFor(student)).thenReturn(testResult);
        Assertions.assertEquals(testServiceImpl.executeTestFor(student).getRightAnswersCount(), 1);
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

        @Bean
        public TestServiceImpl testServiceImpl() {return Mockito.mock(TestServiceImpl.class);}
    }
}
