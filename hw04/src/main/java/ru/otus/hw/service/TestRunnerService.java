package ru.otus.hw.service;

import ru.otus.hw.domain.TestResult;

public interface TestRunnerService {
    void run();

    TestResult getTestResult();
}
