package com.viewnext.micro.busqueda.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.viewnext.micro.busqueda.document.BusquedaDocument;

@Repository
public interface BusquedaRepositorio extends ElasticsearchRepository<BusquedaDocument, String> {
	
	@Query("{\"multi_match\":{\"fields\":[\"titulo\",\"autores\"],\"query\":\"?0\",\"fuzziness\":\"AUTO\"}}")
	Page<BusquedaDocument> findByBusqueda(String busqueda, Pageable pageable);
	@Query("{\"bool\":{\"must\":[{\"multi_match\":{\"fields\":[\"titulo\",\"autores\"],\"query\":\"?0\",\"fuzziness\":\"AUTO\"}},{\"match\":{\"categoria.id\":\"?1\"}}]}}")
	Page<BusquedaDocument> findByBusquedaAndCategoriaId(String busqueda, Long categoiraId, Pageable pageable);
	Page<BusquedaDocument> findByCategoriaId(Long categoiraId, Pageable pageable);
	
}
