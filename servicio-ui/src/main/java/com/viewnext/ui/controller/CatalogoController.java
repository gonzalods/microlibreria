package com.viewnext.ui.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.ui.bean.Categoria;

@RestController
public class CatalogoController {

	@RequestMapping("categorias")
	public HttpEntity<?> getCategorias(){
		List<Categoria> categorias = Arrays.asList(
				new Categoria(1L, "Categoría Uno"), 
				new Categoria(2L, "Categoría Dos"));
		return new ResponseEntity<>(categorias, HttpStatus.OK);
	}
}
