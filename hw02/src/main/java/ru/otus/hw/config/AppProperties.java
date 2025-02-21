package ru.otus.hw.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class AppProperties implements TestConfig, TestFileNameProvider {

    // внедрить свойство из application.properties
    private String testFileName;

    // внедрить свойство из application.properties
    private int rightAnswersCountToPass;
    
    public AppProperties(@Value("${test.fileName}") String testFileName,
                         @Value("${test.rightAnswersCountToPass}") int rightAnswersCountToPass
                         ) {
        this.testFileName = testFileName;
        this.rightAnswersCountToPass = rightAnswersCountToPass;
    }


}
