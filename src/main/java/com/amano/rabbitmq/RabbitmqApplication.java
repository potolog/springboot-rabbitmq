package com.amano.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

@SpringBootApplication
public class RabbitmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqApplication.class, args);
	}

    //  @Value("${myqueue}")
    @Value("${spring.rabbitmq.queue}")
	String queue;

	/*
	@Bean
    Queue queue() {
		return new Queue(queue, false);
	}
	*/

	@Autowired
	Producer producer;

	@Bean
	CommandLineRunner sender(Producer producer) {
		return args -> {
			producer.sendTo(queue, "Hello !!!");
		};
	}

}