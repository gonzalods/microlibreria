package com.viewnext.microlibreria.catalogo.servicio;

import java.util.List;
import java.util.Optional;

import com.viewnext.microlibreria.catalogo.entity.Categoria;

public interface CategoriaServicio {

	public Categoria guardaCategoria(Categoria categoria);
	public Categoria actualizarCategoria(Categoria categoria);
	public Categoria eliminarCategoria(Long id);
	public Optional<Categoria> buscarPorId(Long id);
	public List<Categoria> todasCategorias();
}
