package com.amano.rabbitmq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    // 타이머에 의한 RabbitMQ 메시지 전송 여부
    @Value("${schedule.rabbitmq.timer}")
    String scheduleRabbitTimer;

    // cron 에 의한 RabbitMQ 메시지 전송 여부
    @Value("${schedule.rabbitmq.cron}")
    String scheduleRabbitCron;

    // RabbitMQ - host
    @Value("${spring.rabbitmq.host}")
    String host;

    // RabbitMQ - port
    @Value("${spring.rabbitmq.port}")
    String port;

    // RabbitMQ - username
    @Value("${spring.rabbitmq.username}")
    String username;

    // RabbitMQ - password
    @Value("${spring.rabbitmq.password}")
    String password;

    // RabbitMQ - virtualhost
    @Value("${spring.rabbitmq.virtualhost}")
    String virtualhost;

    // RabbitMQ - exchange
    @Value("${spring.rabbitmq.exchange}")
    String exchange;

    // RabbitMQ - topic
    @Value("${spring.rabbitmq.topic}")
    String topic;

    // RabbitMQ - alarm queue
    @Value("${spring.rabbitmq.queue-alarm}")
    String queue_alarm;

    // RabbitMQ - request queue
    @Value("${spring.rabbitmq.queue-request}")
    String queue_request;

    // RabbitMQ - response queue
    @Value("${spring.rabbitmq.queue-response}")
    String queue_response;

    // RabbitMQ - response queue 1
    @Value("${spring.rabbitmq.queue-response1}")
    String queue_response_1;

    // RabbitMQ - response queue 2
    @Value("${spring.rabbitmq.queue-response2}")
    String queue_response_2;

}
