package com.viewnext.cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public HeaderHttpSessionStrategy sessionStrategy(){
		return new HeaderHttpSessionStrategy();
	}
	
	@Configuration
	public static class SecurityConfiguration extends WebSecurityConfigurerAdapter{

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().authorizeRequests().anyRequest().authenticated()
			.and()
			.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		}
		
	}
}
