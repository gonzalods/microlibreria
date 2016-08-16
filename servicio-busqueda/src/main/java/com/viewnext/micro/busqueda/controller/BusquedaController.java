package com.viewnext.micro.busqueda.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.micro.busqueda.bean.FiltroBusqueda;
import com.viewnext.micro.busqueda.bean.Libro;
import com.viewnext.micro.busqueda.bean.RespuestaBusqueda;
import com.viewnext.micro.busqueda.document.BusquedaDocument;
import com.viewnext.micro.busqueda.servicio.BusquedaServicio;

@RestController
@CrossOrigin
@RefreshScope
public class BusquedaController {

	@Autowired
	private BusquedaServicio servicio;
	
	@Value("${busqueda.excludido}")
	private Long idExcluido;
	
	@RequestMapping(value="/busqueda", method = RequestMethod.POST, consumes="application/json")
	public HttpEntity<?> busqueda(@RequestBody FiltroBusqueda filtro){
		
		
		ResponseEntity<?> respuesta = null;
		
		try{
			Page<BusquedaDocument> resultado = servicio.busqueda(filtro);
			if(resultado.hasContent()){
				respuesta = crearRespuesta(resultado);
			}else{
				respuesta = new ResponseEntity<>(filtro, HttpStatus.NOT_FOUND);
			}
			return respuesta;
		}catch(IllegalArgumentException e){
			return new ResponseEntity<>(filtro, HttpStatus.BAD_REQUEST);
		}
	}
	
	private ResponseEntity<RespuestaBusqueda> crearRespuesta(Page<BusquedaDocument> result){
		
		List<Libro> libros = result.getContent().stream()
				.filter(item -> {
					return Long.parseLong(item.getId()) != idExcluido;
				})
				.map(item ->{
					Libro lib = new Libro();
					lib.setId(Long.parseLong(item.getId()));
					lib.setTitulo(item.getTitulo());
					lib.setCategoria(item.getCategoria().getDescripcion());
					lib.setAutores(item.getAutores());
					lib.setPrecio(item.getPrecio());
					return lib;
				}).collect(Collectors.toList());
		RespuestaBusqueda respuesta = new RespuestaBusqueda();
		respuesta.setLibros(libros);
		respuesta.setNumPaginaActual(result.getNumber());
		respuesta.setNumTotalLibros(result.getTotalElements());
		respuesta.setTamanoPagina(result.getSize());
		respuesta.setTotalPaginas(result.getTotalPages());
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
}
