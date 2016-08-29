package com.viewnext.admin;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.viewnext.admin.config.AdminWebMvcConfigurerAdapter;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients
public class ServicioAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioAdminApplication.class, args);
	}
	
	@Bean
	public AlwaysSampler defaultSampler() {
		return new AlwaysSampler();
	}
//	@Bean
//	@LoadBalanced
	RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.errorHandler(new ResponseErrorHandler() {
			
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				// TODO Auto-generated method stub
				
			}
		}).build();
	}
	
	@Bean
	public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
		return new AdminWebMvcConfigurerAdapter();
	}
}
