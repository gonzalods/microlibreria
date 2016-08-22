package com.viewnext.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.viewnext.admin.config.AdminWebMvcConfigurerAdapter;

@SpringBootApplication
@EnableEurekaClient
public class ServicioAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioAdminApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
		return new AdminWebMvcConfigurerAdapter();
	}
	
	@Configuration
	public static class SecurityConfiguration extends WebSecurityConfigurerAdapter{

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.httpBasic().disable();
			http
				.authorizeRequests().anyRequest().hasRole("ADMIN")
			.and()
				.csrf()
					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		}
		
	}
}
