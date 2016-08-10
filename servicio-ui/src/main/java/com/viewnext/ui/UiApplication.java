package com.viewnext.ui;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class UiApplication {

	@RequestMapping("/user")
	public Principal user (Principal user){
		return user;
	}
	
	@RequestMapping("/token")
	public Map<String, String> token(HttpSession session){
		return Collections.singletonMap("token", session.getId());
	}
	
	public static void main(String[] args) {
		SpringApplication.run(UiApplication.class, args);
	}
	
	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter{

		@Autowired
		private DataSource datasource;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.httpBasic()
			.and()
				.authorizeRequests()
					.antMatchers("/index.html", "/fragments/novedades.html", 
							"/fragments/catalogo.html", "/fragments/menu.html",
							"/fragments/cuenta.html",
							"/fragments/login.html","/").permitAll()
					.anyRequest().authenticated()
				.and()
					.csrf()
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication().dataSource(datasource);
		}
	}
}
