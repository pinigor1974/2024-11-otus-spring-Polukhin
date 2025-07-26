package ru.otus.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.config.IntegrationConfig;
import ru.otus.domain.Butterfly;
import ru.otus.gateway.Convertion;

import java.util.*;
import java.util.concurrent.*;

@Service
public class RunningService implements Runnable {

    /*
    Create object ListOf ButterFly - as main object Lock
    user Thread one as filling it with data from generator
    try to synchronize it with filling data finally unlock and notify
    read Thread as soon as it is full and move to wait status
    The read Thread read until it's empty and go to wait
     */
    private ExecutorService service = Executors.newFixedThreadPool(2);
    private final FillingService fillingService ;
    private final ReadService readService;

    public RunningService(ReadService readService, FillingService fillingService) {
        this.readService = readService;
        this.fillingService = fillingService;
    }

    @Override
    public void run() {
        service.submit(this.fillingService);
        service.submit(this.readService);
    }
}
