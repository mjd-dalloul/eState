package com.example.spring_tutorial.service;

import com.example.spring_tutorial.configuration.AppConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Service
@Profile("sender")
public class MessageSenderService {
    static Logger logger
            = LoggerFactory.getLogger(MessageSenderService.class);


    public void sendMessage(String message)
            throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(AppConstant.QUEUE_NAME,
                false,
                false,
                false,
                null);
        channel.basicPublish("",
                AppConstant.QUEUE_NAME,
                null,
                message.getBytes(StandardCharsets.UTF_8));
        logger.debug("[!] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
