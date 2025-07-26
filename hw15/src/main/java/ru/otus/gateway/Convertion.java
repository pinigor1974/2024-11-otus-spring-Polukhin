package ru.otus.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.domain.Butterfly;
import ru.otus.domain.Catterpillar;



@MessagingGateway
public interface Convertion {

    @Gateway(requestChannel = "butterflyChannel", replyChannel = "catterpillarChannel")
    Catterpillar process(Butterfly orderItem);


}
