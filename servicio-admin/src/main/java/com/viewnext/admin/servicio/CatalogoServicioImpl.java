
package com.viewnext.admin.servicio;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.viewnext.admin.bean.Categoria;
import com.viewnext.admin.bean.FiltroBusqueda;
import com.viewnext.admin.bean.Libro;
import com.viewnext.admin.bean.RespuestaBusqueda;
import com.viewnext.admin.client.BusquedaCliente;
import com.viewnext.admin.client.CatalogoCliente;
import com.viewnext.admin.config.AdminProperties;
import com.viewnext.admin.security.SessionTokens;

@Service
@Lazy
public class CatalogoServicioImpl implements CatalogoServicio {

	private static final Logger logger = LoggerFactory.getLogger(CatalogoServicio.class);
	//private AdminProperties adminProperties;
	
	//private String catalogoXsrfToke;
	
	@Autowired
	private CatalogoCliente catalogoCliente;
	@Autowired
	private BusquedaCliente busquedaCliente;
	
//	@Autowired
//	private RestTemplate restTemplateCatalogo;
//	@Autowired
//	private RestTemplate restTemplateBusqueda;
//	@Autowired
//	public CatalogoServicioImpl(AdminProperties adminConfig) {
//		this.adminProperties = adminConfig;
//	}
	
//	@HystrixCommand(/*fallbackMethod="listaCatNoDisponible"*/)
	@Override
	public List<Categoria> todasCategorias() {
//		String url = adminProperties.getCatalogoUrl() + "/categoria/all";
//		ResponseEntity<List<Categoria>> catsResponse = 
//				restTemplateCatalogo.exchange( url, HttpMethod.GET, null, 
//						new ParameterizedTypeReference<List<Categoria>>() {
//						});
		ResponseEntity<List<Categoria>> catsResponse = catalogoCliente.todasCategorias();
		getXsrfToken(catsResponse.getHeaders().get("Set-Cookie"));
		return catsResponse.getBody();
	}
	
	public List<Categoria> listaCatNoDisponible(){
		return Collections.emptyList();
	}

	@Override
	public Categoria buscarCategoiraPorId(Long id) {
		return null;
	}

	@Override
	public void actualizarCategoria(Categoria cat) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nuevaCategoria(Categoria cat) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminarCategoria(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public RespuestaBusqueda buscarPorCriterios(FiltroBusqueda filtro) {
		ResponseEntity<RespuestaBusqueda> busquedaResponse = busquedaCliente.busquedaPorCriterios(filtro);
		return busquedaResponse.getBody();
	}

	public RespuestaBusqueda busquedaNoDisponible(FiltroBusqueda filtro){
		return null;
	}
	
	@Override
	public Libro buscarLibroPorId(Long id) {
		return catalogoCliente.buscarLibroPorId(id);
	}

	@Override
	public void actualizarLibro(Libro libro) {
		logger.info("Se realiza petición para actualizar libro:{}", libro.getId());
		ResponseEntity<?> response = catalogoCliente.actualizarLibro(libro.getId(), libro);
	}

	@Override
	public void nuevoLibro(Libro libro) {

		ResponseEntity<Libro> response = catalogoCliente.nuevoLibro(libro);
	}

	@Override
	public void eliminarLibro(Long id) {
		
		catalogoCliente.eliminarLibro(id);
	}

	private void getXsrfToken(List<String> setCookie){
		if(setCookie != null){
			for (String cookie : setCookie) {
				if(cookie.trim().startsWith("XSRF-TOKEN")){
					String xsrfToken = cookie.trim().split(";")[0]; 
					SessionTokens.get().setCsrf(xsrfToken.trim().split("=")[1]);
					break;
				}
			}
		}
	}
}
