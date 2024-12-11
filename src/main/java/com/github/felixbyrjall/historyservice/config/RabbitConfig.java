package com.github.felixbyrjall.historyservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	@Bean
	public TopicExchange vehicleExchange(@Value("${amqp.exchange.name}") final String exchangeName) {
		return ExchangeBuilder
				.topicExchange(exchangeName)
				.durable(true)
				.build();
	}

	@Bean
	public Queue vehicleQueue() {
		return new Queue("vehicle.searched.queue", true);
	}

	@Bean
	public Binding vehicleQueueBinding(Queue vehicleQueue, TopicExchange vehicleExchange) {
		return BindingBuilder
				.bind(vehicleQueue)
				.to(vehicleExchange)
				.with("vehicle.#");
	}

	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
