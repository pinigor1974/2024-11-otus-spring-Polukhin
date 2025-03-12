package ru.otus.test.hw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.LocalizedIOService;
import ru.otus.hw.service.TestServiceImpl;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class ApplicationTest {
    @Mock
    QuestionDao csvQuestionDao;
    @Mock
    LocalizedIOService ioService;

    TestServiceImpl testService;

    @BeforeEach
    void init() {

        testService = new TestServiceImpl(ioService, csvQuestionDao);
    }

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
