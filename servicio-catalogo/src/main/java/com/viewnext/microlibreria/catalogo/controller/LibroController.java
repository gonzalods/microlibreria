package com.viewnext.microlibreria.catalogo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.microlibreria.catalogo.entity.Libro;
import com.viewnext.microlibreria.catalogo.servicio.CatalogoServicio;

@RestController
@RequestMapping("/catalogo")
@CrossOrigin
public class LibroController {

	@Autowired
	private CatalogoServicio catalogoServicio;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public HttpEntity<Libro> getLibro(@PathVariable Long id){
		Optional<Libro> lib = catalogoServicio.buscarPorId(id);
		ResponseEntity<Libro> response = null;
		if(lib.isPresent()){
			response = new ResponseEntity<Libro>(lib.get(), HttpStatus.OK);
		}else{
			response = new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public HttpEntity<Libro> guardarLibro(@RequestBody Libro libro){
		ResponseEntity<Libro> response = null;
		try{
			Libro nuevo = catalogoServicio.guardarLibro(libro);
			response = new ResponseEntity<Libro>(nuevo, HttpStatus.CREATED);
		}catch(RuntimeException e){
			response = new ResponseEntity<Libro>(libro, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public HttpEntity<Libro> actualizarLibro(@RequestBody Libro libro){
		ResponseEntity<Libro> response = null;
		try{
			Libro modificado = catalogoServicio.actualizarLibro(libro);
			response = new ResponseEntity<Libro>(modificado, HttpStatus.OK);
			
		}catch(RuntimeException e){
			response = new ResponseEntity<Libro>(libro, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public HttpEntity<Libro> eliminarLibro(@PathVariable Long id){
		ResponseEntity<Libro> response = null;
		try{
			Libro borrado = catalogoServicio.eliminarLibro(id);
			response = new ResponseEntity<Libro>(borrado, HttpStatus.OK);
			
		}catch(RuntimeException e){
			response = new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	
}
