package com.viewnext.gateway.servicio;

import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viewnext.gateway.bean.Credenciales;
import com.viewnext.gateway.entity.Users;
import com.viewnext.gateway.repository.AccesoRepositorio;

@Service
@Transactional
public class AccesoServicioImpl implements AccesoServicio {

	private AccesoRepositorio repo;
	
	@Autowired
	public AccesoServicioImpl(AccesoRepositorio repo) {
		super();
		this.repo = repo;
	}


	@Override
	public int actualizarContraseña(String password, String username) {
		return repo.setPasswordFor(password, username);
	}


	@Override
	public void registroCuenta(Credenciales credenciales) {
		if(repo.exists(credenciales.getNombreusuario())){
			
		}
		Users nuevo = new Users(credenciales.getNombreusuario(), 
				credenciales.getPassword(), true);
		nuevo.setAuthority(Collections.singleton("ROLE_USUARIO"));
		repo.save(nuevo);
		
		
	}

}
