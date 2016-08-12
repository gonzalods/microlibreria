package com.viewnext.carrito.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.carrito.entity.ItemCarrito;
import com.viewnext.carrito.servicio.CarritoServicio;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

	@Autowired
	private CarritoServicio carritoServicio;
	
	@RequestMapping(method = RequestMethod.PUT)
	public HttpEntity<?> anadirACarrito(@RequestBody ItemCarrito itemCarrito, Principal user){
		Integer cantidad = carritoServicio.anadirACarrito(user.getName(), itemCarrito);
		
		return new ResponseEntity<>(cantidad, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public HttpEntity<?> eliminarDeCarrito(@PathVariable Long id, Principal user){
		List<ItemCarrito> items = carritoServicio.eliminarDeCarrito(user.getName(), id);
		return new ResponseEntity<>(items, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<?> consultarCarrito(Principal user){
		List<ItemCarrito> items = carritoServicio.consultarCarrito(user.getName());
		return new ResponseEntity<>(items, HttpStatus.OK);
	}
	
	@RequestMapping(value="/cantidad", method=RequestMethod.GET)
	public HttpEntity<?> cantidad(Principal user){
		return new ResponseEntity<>(carritoServicio.cantidad(user.getName()), HttpStatus.OK);
	}
	
}
