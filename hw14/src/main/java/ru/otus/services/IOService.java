package ru.otus.services;

import java.util.List;

public interface IOService {

    List<String> readListWithPrompt(String prompt);
    String resStringWithPrompt(String prompt);
}
