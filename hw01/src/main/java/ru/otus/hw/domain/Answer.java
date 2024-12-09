package ru.otus.hw.domain;

public record Answer(String text) {
    @Override
    public String toString() {
        return text;
    }
}
