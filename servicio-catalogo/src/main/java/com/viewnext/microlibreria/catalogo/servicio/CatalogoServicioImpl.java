package com.viewnext.microlibreria.catalogo.servicio;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.viewnext.microlibreria.catalogo.binding.CatalogoSource;
import com.viewnext.microlibreria.catalogo.entity.Libro;
import com.viewnext.microlibreria.catalogo.repositorio.LibroRepositorio;

@Service
@Transactional
@EnableBinding(CatalogoSource.class)
public class CatalogoServicioImpl implements CatalogoServicio {

	private static final Logger logger = LoggerFactory.getLogger(CatalogoServicio.class);
	
	private LibroRepositorio libroRepo;
	

	@Autowired
	private CatalogoSource catalogoSource;
	
	
	@Autowired
	public CatalogoServicioImpl(LibroRepositorio libroRepo){
		this.libroRepo = libroRepo;
		
	}
	@Override
	public Libro guardarLibro(Libro libro) {
		logger.debug("Se realiza la creación del libro {}", libro.getTitulo());
		libroRepo.save(libro);
		Libro nuevo = libroRepo.findOne(libro.getId());
		logger.debug("Se notifica la creacion del libro {}", libro.getTitulo());
		catalogoSource.create().send(MessageBuilder.withPayload(nuevo).build());
		return nuevo;

	}

	@Override
	public Libro actualizarLibro(Libro libro) {
		if(!libroRepo.exists(libro.getId())){
			throw new RuntimeException("Libro no existe");
		}
		logger.debug("Se realiza la actualización del libro {}", libro.getId());
		libroRepo.save(libro);
		Libro lib = libroRepo.findOne(libro.getId());
		logger.debug("Se notifica la actualización del libro {}", libro.getId());
		catalogoSource.update().send(MessageBuilder.withPayload(lib).build());
		return lib;
	}

	@Override
	public Libro eliminarLibro(Long id) {
		Libro lib = libroRepo.findOne(id);
		if(lib == null){
			throw new RuntimeException("Libro no existe");
		}
		logger.debug("Se realiza la eliminación del libro {}", id);
		libroRepo.delete(lib);
		logger.debug("Se notifica la eliminación del libro {}", id);
		catalogoSource.delete().send(MessageBuilder.withPayload(lib).build());
		return lib;
	}

	@Override
	public Optional<Libro> buscarPorId(Long id) {
		return Optional.ofNullable(libroRepo.findOne(id));
	}
}
