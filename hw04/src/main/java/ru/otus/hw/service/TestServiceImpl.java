package ru.otus.hw.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService localizedIOService;

    private final QuestionDao questionDao;

    @Getter
    private TestResult testResult;

    @Override
    public TestResult executeTestFor(Student student) {
        localizedIOService.printLine("");
        localizedIOService.printLineLocalized("TestService.answer.the.questions");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            localizedIOService.printLine(question.toString());
            var answer = Integer.parseInt(localizedIOService.readString());
            var isAnswerValid = question.answers().get(answer - 1).isCorrect(); // Задать вопрос, получить ответ
            testResult.applyAnswer(question, isAnswerValid);
        }
       this.testResult = testResult;

        return testResult;
    }
}
