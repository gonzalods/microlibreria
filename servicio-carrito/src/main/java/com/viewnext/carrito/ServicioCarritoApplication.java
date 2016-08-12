package com.viewnext.carrito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;

@SpringBootApplication
@EnableRedisRepositories
public class ServicioCarritoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioCarritoApplication.class, args);
	}
	

	@Bean
	public HeaderHttpSessionStrategy sessionStrategy(){
		return new HeaderHttpSessionStrategy();
	}
	
	@Configuration
	public static class SecurityConfiguration extends WebSecurityConfigurerAdapter{

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.httpBasic().disable();
			http
				.authorizeRequests().anyRequest().authenticated()
			.and()
				.csrf()
					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		}
	}
}
