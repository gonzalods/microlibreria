package com.viewnext.microlibreria.catalogo;

import java.util.Collections;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ServicioCatalogoApplication {

	@RequestMapping("/")
	public Map<String, String> saludo(){
		return Collections.singletonMap("saludo", "¿Como estás?");
	}
	public static void main(String[] args) {
		SpringApplication.run(ServicioCatalogoApplication.class, args);
	}
}
