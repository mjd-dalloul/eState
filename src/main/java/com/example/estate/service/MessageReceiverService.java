package com.example.estate.service;

import com.example.estate.repository.ConstantsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("receiver")
public class MessageReceiverService {
    static Logger logger = LoggerFactory.getLogger(MessageReceiverService.class);
    @Autowired
    private ConstantsRepository repository;

//    public static void main(String[] argv)
//            throws IOException, TimeoutException {
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setHost("localhost");
//        Connection connection = connectionFactory.newConnection();
//        Channel channel = connection.createChannel();
//        channel.queueDeclare(AppConstant.QUEUE_NAME,
//                false, false,false, null);
////                durable,  exclusive, autoDelete
//        logger.info("[!] Waiting for messages. To exit press Ctrl+C");
//
//        Consumer consumer = new DefaultConsumer(channel){
//            @Override
//            public void handleDelivery(String consumerTag,
//                                       Envelope envelope,
//                                       AMQP.BasicProperties properties,
//                                       byte[] body)
//                    throws IOException {
//                ObjectMapper mapper = new ObjectMapper();
//                String message = new String(body, StandardCharsets.UTF_8);
//                Shift shift = mapper.readValue(message, Shift.class);
//                logger.info("[x] Message Received' " + message + "'");
//            }
//        };
//        channel.basicConsume(AppConstant.QUEUE_NAME, true, consumer);
//    }
}
