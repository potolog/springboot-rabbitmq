package com.amano.rabbitmq;

import com.fasterxml.jackson.databind.deser.Deserializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.stream.IntStream;

@Component
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

//  @Scheduled(cron = "0/3 * * * * *")
    @Scheduled(cron = "* * * * * *")
    public void onSend() {
        logger.info("Sending message... Start");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        IntStream.range(1, 15000)
                .parallel()
                .forEach(val -> {
                    this.rabbitTemplate.convertAndSend("${spring.rabbitmq.queue}", "Hello, RabbitMQ! 1");
                });
        stopWatch.stop();
        logger.info(stopWatch.toString());
        logger.info("Sending message... End");
    }

    public void sendTo(String routingKey, String message) {
        logger.info(String.format("send message...> %s", message));
        this.rabbitTemplate.convertAndSend(routingKey, message);
    }
}
