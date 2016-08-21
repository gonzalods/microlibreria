package com.viewnext.microlibreria.catalogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@SpringBootApplication
public class ServicioCatalogoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioCatalogoApplication.class, args);
	}
	
	@Bean
	public MessageConverter jackson2MessageConverter(){
		return new MappingJackson2MessageConverter();
	}
	
	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	public static class SecurityConfiguration extends WebSecurityConfigurerAdapter{

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.httpBasic().disable();
			http
				.authorizeRequests()
					.antMatchers(HttpMethod.GET).permitAll()
				.anyRequest().hasRole("ADMIN")
			.and()
				.csrf()
					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
					
			
		}
		
	}
}
