package com.viewnext.microlibreria.catalogo.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.microlibreria.catalogo.entity.Libro;
import com.viewnext.microlibreria.catalogo.servicio.CatalogoServicio;

@RestController
@RequestMapping("/catalogo")
public class LibroController {

	private static final Logger logger = LoggerFactory.getLogger(CatalogoServicio.class);
	
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
			logger.debug("Petición de crear libro {}", libro.getTitulo());
			Libro nuevo = catalogoServicio.guardarLibro(libro);
			response = new ResponseEntity<Libro>(nuevo, HttpStatus.CREATED);
		}catch(RuntimeException e){
			logger.debug("Petición de crear libro {} no se ha podido realizar", libro.getTitulo());
			response = new ResponseEntity<Libro>(libro, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public HttpEntity<Libro> actualizarLibro(@RequestBody Libro libro){
		ResponseEntity<Libro> response = null;
		try{
			logger.debug("Petición de actualizar libro {}", libro.getId());
			Libro modificado = catalogoServicio.actualizarLibro(libro);
			response = new ResponseEntity<Libro>(modificado, HttpStatus.OK);
			
		}catch(RuntimeException e){
			logger.debug("Petición de actualizar libro {} no se ha podido realizar", libro.getId());
			response = new ResponseEntity<Libro>(libro, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public HttpEntity<Libro> eliminarLibro(@PathVariable Long id){
		ResponseEntity<Libro> response = null;
		try{
			logger.debug("Petición de elimianación libro {}", id);
			Libro borrado = catalogoServicio.eliminarLibro(id);
			response = new ResponseEntity<Libro>(borrado, HttpStatus.OK);
			
		}catch(RuntimeException e){
			logger.debug("Petición de actualizar libro {} no se ha podido realizar", id);
			response = new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	
}
