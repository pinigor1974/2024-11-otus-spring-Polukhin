package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            ioService.printLine(question.toString());
            var answer = Integer.parseInt(ioService.readString());
            var isAnswerValid = question.answers().get(answer).isCorrect(); // Задать вопрос, получить ответ
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }
}
