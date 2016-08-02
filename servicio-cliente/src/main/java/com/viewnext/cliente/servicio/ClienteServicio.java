package com.viewnext.cliente.servicio;

import java.util.Optional;

import com.viewnext.cliente.entity.Cliente;

public interface ClienteServicio {

	public Cliente guardarCliente(Cliente cliente);
	public Cliente actualizarCliente(Cliente cliente);
	public Cliente eliminarCliente(Long id);
	public Optional<Cliente> buscarPorNombreusuario(String nombreusuario);
}
