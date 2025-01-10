package ru.otus.test.hw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.QuizApplication;
import ru.otus.hw.dao.CsvQuestionDao;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = QuizApplication.class)
public class QuestionDaoTest {
    @Autowired
    CsvQuestionDao csvQuestionDao;

    @Test
    void testQuestionDao(){
        var questions = csvQuestionDao.findAll();
        Assertions.assertEquals(questions.size(), 3);
    }

}
