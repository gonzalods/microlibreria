package com.viewnext.micro.busqueda.servicio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.viewnext.micro.busqueda.bean.FiltroBusqueda;
import com.viewnext.micro.busqueda.binding.CatalogoSink;
import com.viewnext.micro.busqueda.document.BusquedaDocument;
import com.viewnext.micro.busqueda.repositorio.BusquedaRepositorio;

@Service
@EnableBinding(CatalogoSink.class)
public class BusquedaServicioImpl implements BusquedaServicio {

	private static final Logger logger = LoggerFactory.getLogger(BusquedaServicio.class);
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
				&& (filtro.getCategoria() == null || filtro.getCategoria() ==0)){
			return repositorio.findByBusqueda(filtro.getBusqueda(), pageable);
		}else if((filtro.getBusqueda()==null || filtro.getBusqueda().isEmpty()) 
				&& filtro.getCategoria() != null && filtro.getCategoria() !=0){
			return repositorio.findByCategoriaId(filtro.getCategoria(), pageable);
		}else {
			throw new IllegalArgumentException();
		}
	}
	
	@StreamListener(CatalogoSink.CATALOGO_UPDATE_CREATE)
	public void updateDocBusqueda(BusquedaDocument busqueda){
		logger.info("Se guardan los cambios del libro {} en la búsqueda", busqueda.getId());
		repositorio.save(busqueda);
	}
	
	@StreamListener(CatalogoSink.CATALOGO_DELETE)
	public void deleteDocBusqueda(BusquedaDocument busqueda){
		logger.debug("Se elimia el libro {} en la búsqueda", busqueda.getId());
		repositorio.delete(busqueda);
	}
}
