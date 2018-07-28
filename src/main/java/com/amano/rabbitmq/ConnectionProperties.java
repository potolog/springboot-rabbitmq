package com.amano.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectionProperties {

    @Autowired
    AppConfig appConfig;

    public String getRabbitMQHost() {
        return appConfig.host;
    }

    public String getRabbitMQClient1VHost() {
        return appConfig.virtualhost;
    }

    public String getRabbitMQClient1User() {
        return appConfig.username;
    }

    public String getRabbitMQClient1Pass() {
        return appConfig.password;
    }

    public String getRabbitMQClient2VHost() {
        return appConfig.virtualhost;
    }

    public String getRabbitMQClient2User() {
        return appConfig.username;
    }

    public String getRabbitMQClient2Pass() {
        return appConfig.password;
    }

    public int getMinConsumers() {
        return 5;
    }

    public int getPrefetchCount() {
        return 5;
    }

}
