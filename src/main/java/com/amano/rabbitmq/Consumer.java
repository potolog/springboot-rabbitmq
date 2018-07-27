package com.amano.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    /*
    @RabbitListener(queues = "${spring.rabbitmq.queue-response-1}")
    public void handler(String message) {
        logger.info(String.format("consemer 1....> %s", message));
    }
    */

    @RabbitListener(queues = "${spring.rabbitmq.queue-response-2}")
    public void handler_2(String message) {
        logger.info(String.format("consemer 2....> %s", message));
    }

}
