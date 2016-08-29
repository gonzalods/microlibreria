package com.viewnext.microlibreria.catalogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

@SpringBootApplication
@EnableEurekaClient
public class ServicioCatalogoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioCatalogoApplication.class, args);
	}
	
	@Bean
	public MessageConverter jackson2MessageConverter(){
		return new MappingJackson2MessageConverter();
	}
	
	@Bean
	public AlwaysSampler defaultSampler() {
		return new AlwaysSampler();
	}

}
