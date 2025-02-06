package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.TestConfig;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final LocalizedIOService localizedIOService;

    @Override
    public void showResult(TestResult testResult) {
        localizedIOService.printLine("");
        localizedIOService.printLineLocalized("ResultService.test.results");
        localizedIOService.printFormattedLine(
                localizedIOService.getMessage("ResultService.student"), testResult.getStudent().getFullName());
        localizedIOService.printFormattedLine(
                localizedIOService.getMessage("ResultService.answered.questions.count"),
                        testResult.getAnsweredQuestions().size());
        localizedIOService.printFormattedLine(
                localizedIOService.getMessage("ResultService.right.answers.count"),
                testResult.getRightAnswersCount());

        if (testResult.getRightAnswersCount() >= testConfig.getRightAnswersCountToPass()) {
            localizedIOService.printLineLocalized(localizedIOService.getMessage("ResultService.passed.test"));
            return;
        }
        localizedIOService.printLineLocalized("ResultService.fail.test");
    }
}
