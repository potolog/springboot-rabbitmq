package com.amano.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableRabbit
public class RabbitMQConfiguration {

    @Autowired
    ConnectionProperties connect;

    // client1 exchanges
    @Bean
    public TopicExchange client1Exchange() {
        TopicExchange ex = new TopicExchange("ex_client1");
        ex.setAdminsThatShouldDeclare(client1());
        return ex;
    }

    // client2 exchange
    @Bean
    public TopicExchange client2Exchange() {
        TopicExchange ex = new TopicExchange("ex_client2");
        ex.setAdminsThatShouldDeclare(client2());
        return ex;
    }

    @Bean
    public Queue client1Queue() {
        Queue queue = new Queue("client1_queue");
        queue.setAdminsThatShouldDeclare(client1());
        return queue;
    }

    @Bean
    public Binding client1Binding() {
        Binding binding = BindingBuilder.bind(client1Queue())
                .to(client1Exchange())
                .with("client1_key");
        binding.setAdminsThatShouldDeclare(client1());
        return binding;
    }


    @Bean
    public Queue client2Queue() {
        Queue queue = new Queue("client2_queue");
        queue.setAdminsThatShouldDeclare(client2());
        return queue;
    }

    @Bean
    public Binding client2Binding() {
        Binding binding = BindingBuilder.bind(client2Queue())
                .to(client2Exchange())
                .with("client2_key");
        binding.setAdminsThatShouldDeclare(client2());
        return binding;
    }

    @Bean
    @Primary
    public ConnectionFactory connectionFactory() {
        SimpleRoutingConnectionFactory connectionFactory = new SimpleRoutingConnectionFactory();
        Map<Object, ConnectionFactory> targetConnectionFactories = new HashMap<>();
        targetConnectionFactories.put("client1", client1ConnectionFactory());
        targetConnectionFactories.put("client2", client2ConnectionFactory());
        connectionFactory.setTargetConnectionFactories(targetConnectionFactories);
        return connectionFactory;
    }

    @Bean
    public ConnectionFactory client1ConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(connect.getRabbitMQHost());
        connectionFactory.setVirtualHost(connect.getRabbitMQClient1VHost());
        connectionFactory.setUsername(connect.getRabbitMQClient1User());
        connectionFactory.setPassword(connect.getRabbitMQClient1Pass());
        return connectionFactory;
    }

    @Bean
    public ConnectionFactory client2ConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(connect.getRabbitMQHost());
        connectionFactory.setVirtualHost(connect.getRabbitMQClient2VHost());
        connectionFactory.setUsername(connect.getRabbitMQClient2User());
        connectionFactory.setPassword(connect.getRabbitMQClient2Pass());
        return connectionFactory;
    }

    // You can comment all methods below and remove interface's implementation to use the default serialization / deserialization
    @Bean
    public RabbitTemplate rabbitTemplate() {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Bean
    public TaskExecutor rabbitListenerExecutor() {
        int threads = Integer.valueOf(connect.getMinConsumers()) * 2; // threads = min consumers* no of queues
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threads);
        executor.setMaxPoolSize(threads);
        executor.setThreadNamePrefix("RabbitThreadListener");
        executor.afterPropertiesSet();
        return executor;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory myRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrentConsumers(Integer.valueOf(connect.getMinConsumers()));
        factory.setPrefetchCount(Integer.valueOf(connect.getPrefetchCount()));
        factory.setTaskExecutor(rabbitListenerExecutor());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean
    public RabbitAdmin client1() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(client1ConnectionFactory());
        rabbitAdmin.afterPropertiesSet();
        return rabbitAdmin;
    }

    @Bean
    public RabbitAdmin client2() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(client2ConnectionFactory());
        rabbitAdmin.afterPropertiesSet();
        return rabbitAdmin;
    }

}
