package com.viewnext.microlibreria.catalogo;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	//@Bean
	public Queue createQueue(){
		return new Queue("CreateQ", false);
	}
	
	//@Bean
	public Queue updateQueue(){
		return new Queue("UpdateQ", false);
	}
	
	//@Bean
	public Queue deleteQueue(){
		return new Queue("DeleteQ", false);
	}
	
	//@Bean
	public TopicExchange exchange() {
		return new TopicExchange("exchange");
	}
	
	//@Bean
	Binding bindingExchangeCreate(Queue createQueue, TopicExchange exchange) {
		return BindingBuilder.bind(createQueue).to(exchange).with("CreateQ");
	}
	
	//@Bean
	Binding bindingExchangeUpdate(Queue updateQueue, TopicExchange exchange) {
		return BindingBuilder.bind(updateQueue).to(exchange).with("UpdateQ");
	}

	//@Bean
	Binding bindingExchangeDelete(Queue deleteQueue, TopicExchange exchange) {
		return BindingBuilder.bind(deleteQueue).to(exchange).with("DeleteQ");
	}
	
	@Bean
	public MessageConverter jackson2MessageConverter(){
		return new MappingJackson2MessageConverter();
	}
}
