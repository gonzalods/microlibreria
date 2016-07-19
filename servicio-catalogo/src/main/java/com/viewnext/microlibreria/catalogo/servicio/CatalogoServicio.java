package com.viewnext.microlibreria.catalogo.servicio;

import java.util.Optional;

import com.viewnext.microlibreria.catalogo.entity.Libro;

public interface CatalogoServicio{
	
	public Libro guardarLibro(Libro libro);
	public Libro actualizarLibro(Libro libro);
	public Libro eliminarLibro(Long id);
	public Optional<Libro> buscarPorId(Long id);
}
