package com.viewnext.microlibreria.catalogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ServicioCatalogoApplication {

	@RequestMapping("/")
	public ResponseEntity<Saludo> saludo(){
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	public static void main(String[] args) {
		SpringApplication.run(ServicioCatalogoApplication.class, args);
	}
}

class Saludo{
	private String saludo;
	private String app;
	public Saludo(String saludo, String app) {
		super();
		this.saludo = saludo;
		this.app = app;
	}
	public String getSaludo() {
		return saludo;
	}
	public void setSaludo(String saludo) {
		this.saludo = saludo;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	
}
