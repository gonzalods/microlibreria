package com.viewnext.micro.busqueda.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.viewnext.micro.busqueda.document.BusquedaDocument;

@Repository
public interface BusquedaRepositorio extends ElasticsearchRepository<BusquedaDocument, String> {
	
	//@Query("{\"fuzzy_like_this_field\":{\"busqueda\":{\"like_text\":\"?0\"}}}")
	@Query("{\"fuzzy_like_this\":{\"fields\":[\"titulo\",\"autores\"], \"like_text\":\"?0\"}}}")
	Page<BusquedaDocument> findByBusqueda(String busqueda, Pageable pageable);
	@Query("{\"bool\":{\"must\":[{\"fuzzy_like_this\":{\"fields\":[\"titulo\",\"autores\"],\"like_text\":\"?0\"}},{\"match\":{\"categoria.id\":\"?1\"}}]}}")
	Page<BusquedaDocument> findByBusquedaAndCategoriaId(String busqueda, Long categoiraId, Pageable pageable);
	Page<BusquedaDocument> findByCategoriaId(Long categoiraId, Pageable pageable);
	
}
