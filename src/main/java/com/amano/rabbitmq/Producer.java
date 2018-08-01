package com.amano.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
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
//  @Scheduled(cron = "*/5 * * * * *")
    public void onCronSchedule() {
        logger.info("producer.onCron : Sending message... Start");

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

    // 이벤트 메시지 전송
    public void sendTo(String routingKey, String message) {
        logger.info(String.format("\n==> send message to [%s]\n%s", routingKey, message));
        this.rabbitTemplate.convertAndSend(routingKey, message);
    }

}
