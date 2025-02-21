package ru.otus.hw.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    @Getter
    private TestResult testResult;


    @Override
    public void run() {
        var student = studentService.getStudent();
        var testResult = testService.executeTestFor(student);
        this.testResult = testResult;
        resultService.showResult(testResult);
    }
}
