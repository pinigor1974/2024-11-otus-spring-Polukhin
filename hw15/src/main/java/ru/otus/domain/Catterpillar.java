package ru.otus.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@RequiredArgsConstructor
@Setter
@Getter
public class Catterpillar {

    private String name;

    private LocalDate birthDate;

}
