package ru.otus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.otus.domain.Butterfly;
import ru.otus.gateway.Convertion;
import ru.otus.services.FillingService;
import ru.otus.services.ReadService;
import ru.otus.services.RunningService;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class IntegrationConfig {

	@Autowired
    private RunningService runningService;
    
    public static final Queue<Butterfly> ITEMS = new ArrayDeque<>(100);

    
    
    
    @Bean
    public QueueChannel butterflyChannel() {
        return MessageChannels.queue( 10 ).get();
    }

    @Bean
    public PublishSubscribeChannel catterpillarChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow transformFlow() {
        return IntegrationFlows.from( butterflyChannel() )
                .split()
                .handle( "transformService", "transform" )
                .aggregate()
                .channel( catterpillarChannel() )
                .get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedDelay(2, TimeUnit.SECONDS).maxMessagesPerPoll(10).get();
    }

    @Scheduled(fixedDelay = 10L)
    public void runService(){
        var r = new Thread(runningService);
        r.start();
    }

}
