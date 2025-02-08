package ru.otus.test.hw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.QuizApplication;
import ru.otus.hw.dao.CsvQuestionDao;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = QuizApplication.class)
@ContextConfiguration(initializers = {QuestionDaoTest.ContextInitializer.class})

public class QuestionDaoTest {
    @Autowired
    CsvQuestionDao csvQuestionDao;

    @Test
    void testQuestionDao(){
        var questions = csvQuestionDao.findAll();
        Assertions.assertEquals(questions.size(), 3);
        var question = "Есть ли жизнь на Марсе?";
        Assertions.assertEquals(questions.get(0).text(), question);
    }
    static class ContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(

                    "spring.shell.interactive.enabled=false",
                    "spring.shell.noninteractive.enabled=false",
                    "spring.shell.context.close=false"

            ).applyTo(context.getEnvironment());
        }
    }
}
