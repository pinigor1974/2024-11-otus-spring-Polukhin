package ru.otus.hw.domain;

import java.util.List;

public record Question(String text, List<Answer> answers) {

    @Override
    public String toString() {
        return  text + answers.stream().reduce(
                new Pair(new StringBuilder(), 1), (pair, a) ->
                        (new Pair(pair.sb().append("\n - ").append(pair.pos()).append(": ").append(a), pair.pos() + 1)),
                (p1, p2) -> (new Pair(p1.sb().append(p2.sb()), p1.pos() + p2.pos()))).sb().toString();
    }
}

