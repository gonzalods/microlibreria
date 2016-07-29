package com.viewnext.micro.busqueda;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@SpringBootApplication
public class Application implements RabbitListenerConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public MessageConverter jackson2MessageConverter(){
		return new MappingJackson2MessageConverter();
	}
	
	@Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(jackson2MessageConverter());
        return factory;
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

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
		
	}
}
