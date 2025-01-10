package ru.otus.hw.domain;

import lombok.Data;


public record Student(String firstName, String lastName, Double Score) {
    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

}
