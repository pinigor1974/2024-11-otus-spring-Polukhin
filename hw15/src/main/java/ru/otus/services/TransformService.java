package ru.otus.services;

import org.springframework.stereotype.Service;
import ru.otus.domain.Butterfly;
import ru.otus.domain.Catterpillar;

import java.time.LocalDate;

@Service
public class TransformService {

    public Catterpillar transform(Butterfly butterFly){
        var caterpillar = new Catterpillar();
        caterpillar.setBirthDate(LocalDate.now());
        caterpillar.setName(butterFly.getName());
        return caterpillar;
    }
}
