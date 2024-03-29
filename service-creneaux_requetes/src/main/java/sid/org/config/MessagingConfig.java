package sid.org.config;

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

	public static final String QUEUE = "service_chat";
	public static final String QUEUE1 = "service_note";
	public static final String QUEUE2 = "service_closeChat";
	public static final String EXCHANGE = "service_exchange";
	public static final String ROUTIN_KEY = "service.routingKeyChat";
	public static final String ROUTIN_KEY1 = "service.routingKey";
	public static final String ROUTIN_KEY2 = "service.routingKeyCloseChat";

	@Bean
	public Queue chatqueue() {

		return new Queue(QUEUE, false);
	}

	@Bean
	public Queue noteQueue() {
		return new Queue(QUEUE1, false);
	}

	@Bean
	public Queue chatcloseQueue() {
		return new Queue(QUEUE2, false);
	}

	@Bean
	public TopicExchange exchange() {

		return new TopicExchange(EXCHANGE);
	}

	@Bean
	public Binding chatBinding(Queue chatqueue, TopicExchange exchange) {

		return BindingBuilder.bind(chatqueue).to(exchange).with(ROUTIN_KEY);
	}

	@Bean
	public Binding notebinding(Queue noteQueue, TopicExchange exchange) {
		return BindingBuilder.bind(noteQueue).to(exchange).with(ROUTIN_KEY1);
	}

	@Bean
	public Binding closeChatbinding(Queue noteQueue, TopicExchange exchange) {
		return BindingBuilder.bind(noteQueue).to(exchange).with(ROUTIN_KEY2);
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