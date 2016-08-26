package com.viewnext.admin.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.viewnext.admin.bean.Categoria;
import com.viewnext.admin.bean.Libro;

@FeignClient(name="servicio-catalogo", /*url="${admin.catalogo-url}"*/ url="http://localhost:9082", 
			configuration=CatalogoClientConfig.class, fallback=CatalogoClienteFallback.class)
public interface CatalogoCliente {

	@RequestMapping(value="/categoria/all")
	ResponseEntity<List<Categoria>> todasCategorias();
	
	@RequestMapping(value="/catalogo/{id}")
	Libro buscarLibroPorId(@PathVariable("id")Long id);
	
	@RequestMapping(value="/categoria/{id}", method=RequestMethod.PUT)
	ResponseEntity<Libro> actualizarLibro(@PathVariable("id")Long id, Libro libro);
	
	@RequestMapping(value="/categoria", method=RequestMethod.POST)
	ResponseEntity<Libro> nuevoLibro(Libro libro);
	
	@RequestMapping(value="/catalogo/{id}", method=RequestMethod.DELETE)
	void eliminarLibro(Long id);
}
