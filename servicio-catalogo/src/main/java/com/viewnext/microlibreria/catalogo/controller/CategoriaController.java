package com.viewnext.microlibreria.catalogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.microlibreria.catalogo.entity.Categoria;
import com.viewnext.microlibreria.catalogo.servicio.CategoriaServicio;

@RestController
@RequestMapping("categoria")
public class CategoriaController {

	@Autowired
	private CategoriaServicio catServicio;
	
	@GetMapping("/all")
	public HttpEntity<?> getAllcategorias(){
		List<Categoria> categorias = catServicio.todasCategorias();
		return new ResponseEntity<>(categorias, HttpStatus.OK);
	}
}
