package ru.otus.test.hw;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Assertions;
import ru.otus.hw.Application;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.LocalizedIOService;
import ru.otus.hw.service.TestService;
import ru.otus.hw.service.TestServiceImpl;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest {
    @MockBean
    QuestionDao csvQuestionDao;
    @MockBean
    LocalizedIOService ioService;
    @Autowired
    TestServiceImpl testService;

    @Test
    void shouldCheckTestServiceImpl() {
        var answers = new ArrayList<Answer>();
        answers.add(new Answer("some answer", true));
        Question q = new Question("some question",answers );
        var questions =  new ArrayList<Question>();
        questions.add(q);
        Mockito.when(csvQuestionDao.findAll()).thenReturn(questions);
        Mockito.when(ioService.readString()).thenReturn("1");
        var student = new Student("Igor", "Polukhin");
        Assertions.assertEquals(testService.executeTestFor(student).getRightAnswersCount(), 1);
    }
}
