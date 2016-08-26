package com.viewnext.admin.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.viewnext.admin.bean.FiltroBusqueda;
import com.viewnext.admin.bean.RespuestaBusqueda;

public class BusquedaClienteFallback implements BusquedaCliente {

	@Override
	public ResponseEntity<RespuestaBusqueda> busquedaPorCriterios(FiltroBusqueda filtroBusqueda) {
		
		return new ResponseEntity<RespuestaBusqueda>(new RespuestaBusqueda(), HttpStatus.SERVICE_UNAVAILABLE);
	}

}
