package com.example.spring_tutorial.service;

import com.example.spring_tutorial.configuration.AppConstant;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Service
@Profile("receiver")
public class MessageReceiverService {
    static Logger logger = LoggerFactory.getLogger(MessageReceiverService.class);

    public void activeListener() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(AppConstant.QUEUE_NAME,
                false, false,false, null);
        logger.info("[!] Waiting for messages. To exit press Ctrl+C");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                logger.info("[x] Message Received' " + message + "'");
            }
        };
        channel.basicConsume(AppConstant.QUEUE_NAME, true, consumer);
    }
}
