package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Student;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private Student student;

    private final LocalizedIOService localizedIOService;

    @Override
    public Student determineCurrentStudent() {
        var firstName = localizedIOService.readStringWithPromptLocalized("StudentService.input.first.name");
        var lastName = localizedIOService.readStringWithPromptLocalized("StudentService.input.last.name");
        this.student =  new Student(firstName, lastName);
        return this.student;
    }

    @Override
    public Student getStudent() {
        return this.student;
    }
}
