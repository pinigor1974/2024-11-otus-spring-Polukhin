package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class StartingAppService {

    final private StudentService studentService;
    final private TestRunnerService testRunnerService;
    private final LocalizedIOService localizedIOService;

    @ShellMethod(value = " Login and Begin ", key = {"login", "l"})
    public void login(){
        studentService.determineCurrentStudent();
    }

    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    @ShellMethod(value = "start quiz",key = {"start", "s"})
    public void startQuiz(){
        testRunnerService.run();
    }


    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    @ShellMethod(value = "get score", key = {"score"})
    public Double getScore(){
        return
                (double) this.testRunnerService.getTestResult().getRightAnswersCount()
                        / (long) this.testRunnerService.getTestResult().getAnsweredQuestions().size();
    }


    private Availability isPublishEventCommandAvailable(){
        if(Optional.ofNullable(studentService.getStudent()).isEmpty() ){
           var msg = localizedIOService.getMessage("StudentService.input.first.name");
            return Availability.unavailable(msg);
        } else {
            return Availability.available();
        }
    }

}
