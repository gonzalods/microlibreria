package com.viewnext.microlibreria.catalogo.servicio;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.stereotype.Service;

import com.viewnext.microlibreria.catalogo.entity.Libro;
import com.viewnext.microlibreria.catalogo.repositorio.LibroRepositorio;

@Service
@Transactional
public class CatalogoServicioImpl implements CatalogoServicio {

	private static final Logger logger = LoggerFactory.getLogger(CatalogoServicio.class);
	
	private LibroRepositorio libroRepo;
	
	private RabbitMessagingTemplate rabbitTemplate;
	
	@Autowired
	
	public CatalogoServicioImpl(LibroRepositorio libroRepo,  
			RabbitMessagingTemplate rabbitTemplate, 
			@Qualifier(value="jackson2MessageConverter") MessageConverter jackson2Converter){
		this.libroRepo = libroRepo;
		this.rabbitTemplate = rabbitTemplate;
		rabbitTemplate.setMessageConverter(jackson2Converter);
		
	}
	@Override
	public Libro guardarLibro(Libro libro) {
		libroRepo.save(libro);
		Libro nuevo = libroRepo.findOne(libro.getId());
		rabbitTemplate.convertAndSend("catalogo.create", nuevo);
		return nuevo;

	}

	@Override
	public Libro actualizarLibro(Libro libro) {
		if(!libroRepo.exists(libro.getId())){
			throw new RuntimeException("Libro no existe");
		}
		logger.info("Se realiza la actualización del libro {0}", libro.getId());
		libroRepo.save(libro);
		Libro lib = libroRepo.findOne(libro.getId());
		logger.info("Se notifica la actualización del libro {0}", libro.getId());
		rabbitTemplate.convertAndSend("catalogo.update", lib);
		return lib;
	}

	@Override
	public Libro eliminarLibro(Long id) {
		Libro lib = libroRepo.findOne(id);
		if(lib == null){
			throw new RuntimeException("Libro no existe");
		}
		libroRepo.delete(lib);
		rabbitTemplate.convertAndSend("catalogo.delete", lib);
		return lib;
	}

	@Override
	public Optional<Libro> buscarPorId(Long id) {
		return Optional.ofNullable(libroRepo.findOne(id));
	}
}
