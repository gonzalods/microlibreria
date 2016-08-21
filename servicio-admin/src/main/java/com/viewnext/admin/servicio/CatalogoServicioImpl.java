
package com.viewnext.admin.servicio;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.viewnext.admin.bean.Categoria;
import com.viewnext.admin.bean.FiltroBusqueda;
import com.viewnext.admin.bean.Libro;
import com.viewnext.admin.bean.RespuestaBusqueda;
import com.viewnext.admin.config.AdminProperties;
import com.viewnext.admin.security.SessionTokens;

@Service
@Lazy
public class CatalogoServicioImpl implements CatalogoServicio {

	private AdminProperties adminProperties;
	
	private RestTemplate restTemplate;
	
	@Autowired
	public CatalogoServicioImpl(AdminProperties adminConfig, RestTemplateBuilder restTemplateBuilder) {
		this.adminProperties = adminConfig;
		restTemplate = restTemplateBuilder.errorHandler(new ResponseErrorHandler() {
			
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				// TODO Auto-generated method stub
				
			}
		}).build();
	}
	
	@Override
	public List<Categoria> todasCategorias() {
		String url = adminProperties.getCatalogoUrl() + "/categoria/all";
		ResponseEntity<List<Categoria>> catsResponse = 
				restTemplate.exchange( url, HttpMethod.GET, null, 
						new ParameterizedTypeReference<List<Categoria>>() {
						});
		return catsResponse.getBody();
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
		String url = adminProperties.getBusquedaUrl() + "/busqueda";
		

		ResponseEntity<RespuestaBusqueda> busquedaResponse = 
				restTemplate.postForEntity(url, filtro, RespuestaBusqueda.class, Collections.emptyMap());

		if(busquedaResponse.getStatusCode() == HttpStatus.NOT_FOUND){
			return new RespuestaBusqueda();
		}else if(busquedaResponse.getStatusCode() != HttpStatus.OK){
			throw new RuntimeException();
		}

		return busquedaResponse.getBody();
	}

	@Override
	public Libro buscarLibroPorId(Long id) {
		String url = adminProperties.getCatalogoUrl() + "/catalogo/{id}";
		Map<String, String> params = new HashMap<>();
		params.put("id", String.valueOf(id));
		
		return restTemplate.getForObject(url, Libro.class, params);
	}

	@Override
	public void actualizarLibro(Libro libro) {
		String url = adminProperties.getCatalogoUrl() + "/catalogo/{id}";
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(url);
		
		RequestEntity<Libro> reqEntity = new RequestEntity<Libro>(libro, headers(),
				HttpMethod.PUT, uriBuilder.build().toUri());
		
		Map<String, String> params = new HashMap<>();
		params.put("id", String.valueOf(libro.getId()));
		restTemplate.exchange(url, HttpMethod.PUT, reqEntity, Void.class, params);

	}

	@Override
	public void nuevoLibro(Libro libro) {
		String url = adminProperties.getCatalogoUrl() + "/catalogo";
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(url);
		
		RequestEntity<Libro> reqEntity = new RequestEntity<Libro>(libro, headers(),
				HttpMethod.POST, uriBuilder.build().toUri());
		
		ResponseEntity<Libro> response = restTemplate.exchange(url, HttpMethod.POST, reqEntity, Libro.class, Collections.emptyMap());
		if(response.getStatusCode() != HttpStatus.CREATED){
			throw new RuntimeException();
		}
	}

	@Override
	public void eliminarLibro(Long id) {
		String url = adminProperties.getCatalogoUrl() + "/catalogo/{id}";
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(url);
		
		RequestEntity<Libro> reqEntity = new RequestEntity<Libro>(headers(),
				HttpMethod.DELETE, uriBuilder.build().toUri());
		
		Map<String, String> params = new HashMap<>();
		params.put("id", String.valueOf(id));
		restTemplate.exchange(url, HttpMethod.DELETE, reqEntity, Void.class, params);

	}

	private MultiValueMap<String, String> headers(){
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		if(SessionTokens.get() != null){
			headers.add("Cookie", "SESSION=" + SessionTokens.get().getSession());
			headers.add("Cookie", "XSRF-TOKEN=" + SessionTokens.get().getCsrf());
			headers.add("X-XSRF-TOKEN", SessionTokens.get().getCsrf());
		}
		return headers;
	}
}
