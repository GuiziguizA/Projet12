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

	public static final String QUEUE = "service_queue";
	public static final String QUEUE1 = "service_chat";
	public static final String QUEUE2 = "service_closeChat";
	public static final String QUEUE3 = "service_queueMessage";

	public static final String EXCHANGE = "service_exchange";

	public static final String ROUTIN_KEY = "service.routingKey";
	public static final String ROUTIN_KEY1 = "service.routingKeyChat";
	public static final String ROUTIN_KEY2 = "service.routingKeyCloseChat";
	public static final String ROUTIN_KEY3 = "service.routingKeyMessage";

	@Bean
	public Queue chatqueue() {

		return new Queue(QUEUE1, false);
	}

	@Bean
	public Queue queue() {
		return new Queue(QUEUE, false);
	}

	@Bean
	public Queue chatcloseQueue() {
		return new Queue(QUEUE2, false);
	}

	@Bean
	public Queue messageQueue() {
		return new Queue(QUEUE3, false);
	}

	@Bean
	public TopicExchange exchange() {

		return new TopicExchange(EXCHANGE);
	}

	@Bean
	public Binding chatBinding(Queue chatqueue, TopicExchange exchange) {

		return BindingBuilder.bind(chatqueue).to(exchange).with(ROUTIN_KEY1);
	}

	@Bean
	public Binding notebinding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTIN_KEY);
	}

	@Bean
	public Binding chatClosebinding(Queue chatcloseQueue, TopicExchange exchange) {
		return BindingBuilder.bind(chatcloseQueue).to(exchange).with(ROUTIN_KEY2);
	}

	@Bean
	public Binding messagebinding(Queue messageQueue, TopicExchange exchange) {
		return BindingBuilder.bind(messageQueue).to(exchange).with(ROUTIN_KEY3);
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