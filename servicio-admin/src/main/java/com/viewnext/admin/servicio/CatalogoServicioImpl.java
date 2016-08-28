
package com.viewnext.admin.servicio;

import java.util.Collections;
import java.util.List;

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

//	@HystrixCommand(fallbackMethod="busquedaNoDisponible", commandProperties = {
//		      @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE")
//		    })
	@Override
	public RespuestaBusqueda buscarPorCriterios(FiltroBusqueda filtro) {
//		String url = adminProperties.getBusquedaUrl() + "/busqueda";
//
//		ResponseEntity<RespuestaBusqueda> busquedaResponse = 
//				restTemplateBusqueda.postForEntity(url, filtro, RespuestaBusqueda.class, Collections.emptyMap());
//
//		if(busquedaResponse.getStatusCode() == HttpStatus.NOT_FOUND){
//			return new RespuestaBusqueda();
//		}else if(busquedaResponse.getStatusCode() != HttpStatus.OK){
//			throw new RuntimeException();
//		}
		ResponseEntity<RespuestaBusqueda> busquedaResponse = busquedaCliente.busquedaPorCriterios(filtro);
		return busquedaResponse.getBody();
	}

	public RespuestaBusqueda busquedaNoDisponible(FiltroBusqueda filtro){
		return null;
	}
	
	@Override
	public Libro buscarLibroPorId(Long id) {
//		String url = adminProperties.getCatalogoUrl() + "/catalogo/{id}";
//		Map<String, String> params = new HashMap<>();
//		params.put("id", String.valueOf(id));
		
//		return restTemplateCatalogo.getForObject(url, Libro.class, params);
		return catalogoCliente.buscarLibroPorId(id);
	}

	@Override
	public void actualizarLibro(Libro libro) {
//		String url = adminProperties.getCatalogoUrl() + "/catalogo/{id}";
//		Map<String, String> params = new HashMap<>();
//		params.put("id", String.valueOf(libro.getId()));
//		ResponseEntity<?> response = restTemplateCatalogo.exchange(url, 
//				HttpMethod.PUT, 
//				new HttpEntity<>(libro, headers()), 
//				Void.class, 
//				params);
//		if(response.getStatusCode() != HttpStatus.OK)
//			throw new RuntimeException(response.getStatusCode().toString());
		
		ResponseEntity<?> response = catalogoCliente.actualizarLibro(libro.getId(), libro);
	}

	@Override
	public void nuevoLibro(Libro libro) {
//		String url = adminProperties.getCatalogoUrl() + "/catalogo";
//		
//		ResponseEntity<Libro> response = restTemplateCatalogo.exchange(url, 
//				HttpMethod.POST, 
//				new HttpEntity<>(libro, headers()), 
//				Libro.class, 
//				Collections.emptyMap());
//		if(response.getStatusCode() != HttpStatus.CREATED){
//			throw new RuntimeException();
//		}
		ResponseEntity<Libro> response = catalogoCliente.nuevoLibro(libro);
	}

	@Override
	public void eliminarLibro(Long id) {
//		String url = adminProperties.getCatalogoUrl() + "/catalogo/{id}";
//		
//		Map<String, String> params = new HashMap<>();
//		params.put("id", String.valueOf(id));
//		restTemplateCatalogo.exchange(url, 
//				HttpMethod.DELETE, 
//				new HttpEntity<>(new Libro(), headers()), 
//				Void.class, 
//				params);
		
		catalogoCliente.eliminarLibro(id);
	}

//	private MultiValueMap<String, String> headers(){
//		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//		if(SessionTokens.get() != null){
//			headers.add("Cookie", "SESSION=" + SessionTokens.get().getSession());
//			headers.add("X-XSRF-TOKEN", catalogoXsrfToke);
//		}
//		return headers;
//	}
	
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
