package com.viewnext.admin.servicio;

import java.util.List;

import com.viewnext.admin.bean.BusquedaLibro;
import com.viewnext.admin.bean.Categoria;
import com.viewnext.admin.bean.FiltroBusqueda;
import com.viewnext.admin.bean.Libro;
import com.viewnext.admin.bean.RespuestaBusqueda;

public interface CatalogoServicio {

	List<Categoria> todasCategorias();
	Categoria buscarCategoiraPorId(Long id);
	void actualizarCategoria(Categoria cat);
	void nuevaCategoria(Categoria cat);
	void eliminarCategoria(Long id);
	
	RespuestaBusqueda buscarPorCriterios(FiltroBusqueda filtro);
	Libro buscarLibroPorId(Long id);
	void actualizarLibro(Libro libro);
	void nuevoLibro(Libro libro);
	void eliminarLibro(Long id);
	
}
