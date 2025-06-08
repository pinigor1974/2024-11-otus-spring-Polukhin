package ru.otus.hw.rest;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Book not found");
    }
}
