package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.config.ButterflyGenerator;
import ru.otus.domain.Butterfly;
import java.util.Collection;

import static ru.otus.config.IntegrationConfig.ITEMS;

@Service
public class FillingService implements Runnable{

    private final static Logger LOGGER = LoggerFactory.getLogger(FillingService.class);

    @Override
    public void run() {
            synchronized (ITEMS) {
                if (ITEMS.size() < 100) {
                    var generator = new ButterflyGenerator();
                    System.out.println("Add butterfly to queue ");
                    generator.getButterflyes().forEach(b -> ITEMS.add(b));
                    System.out.println("Finish add Butterflys ");
                   
                } else {
                    try {
                        System.out.println("Moving Filling Service to wait stage");
                        ITEMS.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                ITEMS.notifyAll();   
            }

    }
}
