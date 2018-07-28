package com.amano.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableScheduling
public class Scheduler {

    private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    AppConfig appConfig;

    @Autowired
    Producer producer;

    // 타이머가 발생하면 메시지를 전송한다.
    @Scheduled(initialDelay = 5000, fixedDelay = 3000)
    public void sendScheduleMessage() {
        if (appConfig.scheduleRabbitTimer.equals("true")) {
        //  logger.info(String.format("==> sendScheduleMessage"));
            producer.sendTo(appConfig.queue_response_1, "Timer Message Delevery : " + new Date());
            producer.sendTo(appConfig.queue_alarm, "alarm : " + new Date());
        }
    }

    // cron 에 의한 메시지를 전송한다.
    @Scheduled(cron = "*/10 * * * * *")
    public void cronScheduleMessage() {
        if (appConfig.scheduleRabbitCron.equals("true")) {
        //  logger.info(String.format("==> cronScheduleMessage"));
            producer.sendTo(appConfig.queue_response_1, "Cron Message Delevery : " + new Date());
        }
    }

}
