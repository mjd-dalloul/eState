package com.example.estate.service;

import com.example.estate.configuration.AppConstant;
import com.example.estate.domain.dto.Shift;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@Profile("!receiver")
public class MessageSenderService {
    static Logger logger
            = LoggerFactory.getLogger(MessageSenderService.class);

    public void sendMessage(Shift message)
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
               new ObjectMapper().writeValueAsString(message).getBytes(StandardCharsets.UTF_8));
        logger.debug("[!] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
