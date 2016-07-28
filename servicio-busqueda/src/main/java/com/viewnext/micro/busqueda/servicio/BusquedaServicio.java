package com.viewnext.micro.busqueda.servicio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.viewnext.micro.busqueda.bean.FiltroBusqueda;
import com.viewnext.micro.busqueda.document.BusquedaDocument;

public interface BusquedaServicio {

	Page<BusquedaDocument> busqueda(FiltroBusqueda filtro);
	Page<BusquedaDocument> busqueda(FiltroBusqueda filtro, Pageable pageable);
}
