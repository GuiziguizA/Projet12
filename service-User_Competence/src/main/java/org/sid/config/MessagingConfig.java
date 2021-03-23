package org.sid.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

	public static final String QUEUE1 = "service_note";

	public static final String EXCHANGE1 = "service_exchange";

	public static final String ROUTIN_KEY1 = "service.routingKey";

	@Bean
	public Queue queue() {

		return new Queue(QUEUE1);
	}

	@Bean
	public TopicExchange exchange() {

		return new TopicExchange(EXCHANGE1);
	}

	@Bean
	public Binding binding(Queue queue, TopicExchange exchange) {

		return BindingBuilder.bind(queue).to(exchange).with(ROUTIN_KEY1);
	}

	@Bean
	public MessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate template(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(converter());
		return rabbitTemplate;

	}

}
