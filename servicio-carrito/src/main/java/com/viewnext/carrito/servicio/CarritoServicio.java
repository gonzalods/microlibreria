package com.viewnext.carrito.servicio;

import java.util.List;

import com.viewnext.carrito.entity.ItemCarrito;

public interface CarritoServicio {

	public Integer anadirACarrito(String nombreusuario, ItemCarrito item);
	public List<ItemCarrito> eliminarDeCarrito(String nombreusuario, Long id);
	public List<ItemCarrito> consultarCarrito(String nombreusuario);
	public Integer cantidad(String nombreusuario);
}
