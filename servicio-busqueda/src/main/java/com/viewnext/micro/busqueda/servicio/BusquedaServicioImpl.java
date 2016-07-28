package com.viewnext.micro.busqueda.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.viewnext.micro.busqueda.bean.FiltroBusqueda;
import com.viewnext.micro.busqueda.document.BusquedaDocument;
import com.viewnext.micro.busqueda.repositorio.BusquedaRepositorio;

@Service
public class BusquedaServicioImpl implements BusquedaServicio {

	@Autowired
	private BusquedaRepositorio repositorio;
	
	@Override
	public Page<BusquedaDocument> busqueda(FiltroBusqueda filtro) {
		
		return busqueda(filtro, new PageRequest(0, 10));
	}

	@Override
	public Page<BusquedaDocument> busqueda(FiltroBusqueda filtro, Pageable pageable) {
		if(filtro.getBusqueda()!=null && !filtro.getBusqueda().isEmpty() 
				&& filtro.getCategoria() != null && filtro.getCategoria() !=0){
			return repositorio.findByBusquedaAndCategoriaId(filtro.getBusqueda(), filtro.getCategoria(), new PageRequest(0, 10));
		}else if(filtro.getBusqueda()!=null && !filtro.getBusqueda().isEmpty() 
				&& filtro.getCategoria() == null || filtro.getCategoria() ==0){
			return repositorio.findByBusqueda(filtro.getBusqueda(), pageable);
		}else if(filtro.getBusqueda()==null || filtro.getBusqueda().isEmpty() 
				&& filtro.getCategoria() != null && filtro.getCategoria() !=0){
			return repositorio.findByCategoriaId(filtro.getCategoria(), pageable);
		}else {
			throw new IllegalArgumentException();
		}
	}
}
