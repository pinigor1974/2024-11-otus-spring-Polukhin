package ru.otus.services;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleIOService implements ImputStreamService {
    private final Scanner userInput;
    private final PrintStream outputStream;

    public ConsoleIOService(InputStream inputStream, PrintStream outputStream) {
        this.userInput = new Scanner(inputStream);
        this.outputStream = outputStream;
    }

    public void outputString(String s) {
        outputStream.println(s);
    }

    public String resStringWithPrompt(String prompt){
        outputString(prompt);
        return userInput.nextLine();
    }
    public List<String> readListWithPrompt(String prompt){
        outputString(prompt);
        return Arrays.stream(userInput.nextLine().split("\\s")).collect(Collectors.toList());
    }


}
