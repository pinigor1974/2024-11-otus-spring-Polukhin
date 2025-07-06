package ru.otus.services;

import org.springframework.stereotype.Service;

import ru.otus.domain.Butterfly;
import ru.otus.gateway.Convertion;
import static ru.otus.config.IntegrationConfig.ITEMS;


@Service
public class ReadService implements Runnable{


    private final Convertion convertion;

    public ReadService( Convertion convertion) {
        this.convertion = convertion;
    }

    @Override
    public void run() {
            synchronized (ITEMS) {
                if (!ITEMS.isEmpty()) {
                    System.out.println("Start processing items");
                    for(Butterfly b: ITEMS) {
                    	convertion.process(b);
                    	System.out.println("process butterfly " + b.getName());
                    	ITEMS.remove(b);
                    }
                } else {
                    try {
                        System.out.println("Moving Read thread to wait phase");
                        ITEMS.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                ITEMS.notifyAll(); 
            }
    }
}
