package com.amano.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@SpringBootApplication
@EnableScheduling
public class RabbitmqApplication {

    @Autowired
    AppConfig appConfig;

	private static final Logger logger = LoggerFactory.getLogger(RabbitmqApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqApplication.class, args);
	}

	/*
	 * RabbitMQ 에서 사용할 queue 를 만든다.
	 */
    @Bean
    Queue queue_request () {
        return new Queue(appConfig.queue_request, true);
    }

    @Bean
    Queue queue_response () {
        return new Queue(appConfig.queue_response, true);
    }

    @Bean
    Queue queue_response_1 () {
        return new Queue(appConfig.queue_response_1, false);
    }

    @Bean
    Queue queue_response_2 () {
        return new Queue(appConfig.queue_response_2, false);
    }

    @Bean
    Queue queue_alarm () {
        return new Queue(appConfig.queue_alarm, true);
    }

	@Autowired
	Producer producer;

	@Bean
	CommandLineRunner sender(Producer producer) {
		return args -> {
			String message = "{\n" +
					"  \"app_id\": \"amano-kakaopay\",\n" +
					"  \"app_key\": \"39e21cd330993e7361ee7afd9cf57345\",\n" +
                    "  \"billerNoticeKey\": \"1_00cdd25739b73a12af549a3b31764c5e\",\n" +
					"  \"site\": \"한국주차장\",\n" +
					"  \"username\": \"홍길동\",\n" +
					"  \"phone_no\": \"010-1111-2222\",\n" +
					"  \"title\": \"정기권 결제\",\n" +
					"  \"description\": \"2018년 5월 정기권 결제\",\n" +
					"  \"amount\": \"150000\",\n" +
					"  \"expire_date\": \"2018-05-31\",\n" +
					"  \"end_date\": \"2018-05-20\",\n" +
					"  \"url\": \"http://naver.com\"\n" +
					"}";
			producer.sendTo(appConfig.queue_request, message);
		};
	}

}
