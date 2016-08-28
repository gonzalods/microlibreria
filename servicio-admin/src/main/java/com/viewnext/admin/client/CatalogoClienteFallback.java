package com.viewnext.admin.client;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.viewnext.admin.bean.Categoria;
import com.viewnext.admin.bean.Libro;

@Component
public class CatalogoClienteFallback implements CatalogoCliente {

	@Override
	public ResponseEntity<List<Categoria>> todasCategorias() {
		return new ResponseEntity<List<Categoria>>(Collections.emptyList(), HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	public Libro buscarLibroPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Libro> actualizarLibro(Long id, Libro libro) {
		System.out.println("no actualiza. ¿por que?");
		return null;
	}

	@Override
	public ResponseEntity<Libro> nuevoLibro(Libro libro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarLibro(Long id) {
		// TODO Auto-generated method stub

	}

}
