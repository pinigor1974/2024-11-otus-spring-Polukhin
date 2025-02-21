package ru.otus.hw.domain;

import java.util.List;

public record Question(String text, List<Answer> answers) {

    @Override
    public String toString() {
        return "Question: " + text  + answers.stream().reduce(
                new StringBuilder(), (sb, a) -> (sb.append("\n - Answer: ").append(a)),
                (sb1,sb2) -> (sb1.append(sb2)));
    }
}