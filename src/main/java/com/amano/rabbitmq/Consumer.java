package com.amano.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void handler(String message) {
        logger.info(String.format("consemer....> %s", message));
    }

}
