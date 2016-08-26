package com.viewnext.admin.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.viewnext.admin.bean.FiltroBusqueda;
import com.viewnext.admin.bean.RespuestaBusqueda;

@FeignClient(name="servicio-busqueda", /*url="${admin.budqueda-url}"url="http://localhost:9081",*/
			configuration=CatalogoClientConfig.class, fallback=BusquedaClienteFallback.class)
public interface BusquedaCliente {

	@RequestMapping(value="/busqueda", method=RequestMethod.POST)
	ResponseEntity<RespuestaBusqueda> busquedaPorCriterios(FiltroBusqueda filtroBusqueda);
}
