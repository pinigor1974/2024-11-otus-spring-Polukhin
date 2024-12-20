package ru.otus.test.hw;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Assertions;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.TestServiceImpl;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfiguration.class})
public class ApplicationTest {

    @Test
    void testService() {
        var csvQuestionDao = mock(QuestionDao.class);
        var answers = new ArrayList<Answer>();
        answers.add(new Answer("some answer", true));
        Question q = new Question("some question",answers );
        var questions =  new ArrayList<Question>();
        questions.add(q);
        Mockito.when(csvQuestionDao.findAll()).thenReturn(questions);
        var ioService = mock(IOService.class);
        Mockito.when(ioService.readString()).thenReturn("1");
        var testServiceImpl = new TestServiceImpl(ioService, csvQuestionDao);
        var student = new Student("Igor", "Polukhin");
        Assertions.assertEquals(testServiceImpl.executeTestFor(student).getRightAnswersCount(), 1);
    }
}
