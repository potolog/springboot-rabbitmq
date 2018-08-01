package com.amano.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.UnsupportedEncodingException;

@Component
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = "${spring.rabbitmq.queue-alarm}")
    public void reveicer_alarm(String message) {
        logger.info(String.format("\n==> receive message from [ALARM]\n%s", message));
    }

//    @RabbitListener(queues = "${spring.rabbitmq.queue-request}")
//    public void reveicer_request(String message) {
//        logger.info(String.format("\n==> receive message from [Request]\n%s", message));
//    }

    @RabbitListener(queues = "${spring.rabbitmq.queue-response}")
    public void reveicer_response(String message) {
        logger.info(String.format("\n==> receive message from [Response]\n%s", message));
    }

//    @RabbitListener(queues = "${spring.rabbitmq.queue-response-1}")
//    public void reveicer_response_1(String message) {
//        logger.info(String.format("\n==> receive message from [Response-1]\n%s", message));
//    }

    @RabbitListener(queues = "${spring.rabbitmq.queue-response-2}")
    public void reveicer_response_2(String message) {
        logger.info(String.format("\n==> receive message from [Response-2]\n%s", message));
    }

//    @RabbitListener(queues = "${spring.rabbitmq.queue-response-3}")
//    public void reveicer_response_3(String message) {
//        logger.info(String.format("\n==> receive message from [Response-2]\n%s", message));
//    }

}
