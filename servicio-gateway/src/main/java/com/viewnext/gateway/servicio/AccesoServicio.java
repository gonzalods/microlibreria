package com.viewnext.gateway.servicio;

import com.viewnext.gateway.bean.Credenciales;

public interface AccesoServicio {

	int actualizarContraseña(String password, String username);
	void registroCuenta(Credenciales credenciales);
}
