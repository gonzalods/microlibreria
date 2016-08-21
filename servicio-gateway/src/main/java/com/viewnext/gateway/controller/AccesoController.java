package com.viewnext.gateway.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.gateway.bean.Credenciales;
import com.viewnext.gateway.servicio.AccesoServicio;

@RestController
public class AccesoController {

	@Autowired
	private AccesoServicio accesoServicio;
	
	@GetMapping("/user")
	public Map<String, Object> user (Principal user){
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("name", user.getName());
		map.put("roles", AuthorityUtils.authorityListToSet(((Authentication)user)
				.getAuthorities()));
		return map;
	}
	
	@GetMapping("/token")
	public Map<String, String> token(HttpSession session){
		String sessionId = session.getId();
		return Collections.singletonMap("token", sessionId);
	}
	
	@PutMapping("/password")
	public HttpEntity<?> password(@RequestBody Map<String, String> password, Principal user){
		accesoServicio.actualizarContraseña(password.get("password"), user.getName());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	@PostMapping("/registrarse")
	public HttpEntity<?> registro(@RequestBody Credenciales credenciales){
		accesoServicio.registroCuenta(credenciales);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
