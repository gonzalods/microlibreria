package com.viewnext.carrito.entity;
      
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("carrito")
public class Carrito {

	@Id
	private String nombreusuario;
	
	private Map<String, ItemCarrito> items;
	public Carrito(){
		items = new HashMap<>();
	}
	public String getNombreusuario() {
		return nombreusuario;
	}
	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}
	public Map<String, ItemCarrito> getItems() {
		return items;
	}
	public void setItems(Map<String, ItemCarrito> items) {
		this.items = items;
	}
	
}
