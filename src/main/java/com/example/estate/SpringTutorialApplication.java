package com.example.estate;

import com.example.estate.configuration.AppConstant;
import com.example.estate.domain.dto.Shift;
import com.example.estate.domain.entity.Constants;
import com.example.estate.repository.ConstantsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class SpringTutorialApplication  {

	public static void main(String[] args) {
		SpringApplication.run(SpringTutorialApplication.class, args);
	}

}
@Profile("receiver")
@Component
class ReceiverInitializer  implements CommandLineRunner {
	static Logger logger = LoggerFactory.getLogger(ReceiverInitializer.class);
	@Autowired
	ConstantsRepository repository;
	@Override
	public void run(String... args) throws Exception {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(AppConstant.QUEUE_NAME,
				false, false,false, null);
//                durable,  exclusive, autoDelete

		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag,
									   Envelope envelope,
									   AMQP.BasicProperties properties,
									   byte[] body)
					throws IOException {
				ObjectMapper mapper = new ObjectMapper();
				String message = new String(body, StandardCharsets.UTF_8);
				Shift shift = mapper.readValue(message, Shift.class);
				final Constants constants = repository.findByKey(AppConstant.share);
				constants.setValue(constants.getValue() + shift.getOffset());
				repository.save(constants);
				logger.info("[x] Message Received' " + message + "'");
			}
		};
		channel.basicConsume(AppConstant.QUEUE_NAME, true, consumer);
	}
}