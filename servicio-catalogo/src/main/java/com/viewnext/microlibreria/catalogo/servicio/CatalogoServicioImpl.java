package com.viewnext.microlibreria.catalogo.servicio;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viewnext.microlibreria.catalogo.entity.Libro;
import com.viewnext.microlibreria.catalogo.repositorio.LibroRepositorio;

@Service
@Transactional
public class CatalogoServicioImpl implements CatalogoServicio {

	@Autowired
	private LibroRepositorio libroRepo;
	
	@Override
	public Libro guardarLibro(Libro libro) {
		return libroRepo.save(libro);

	}

	@Override
	public Libro actualizarLibro(Libro libro) {
		Libro lib = libroRepo.findOne(libro.getId());
		if(lib == null){
			throw new RuntimeException("Libro no existe");
		}
		return libroRepo.save(libro);
	}

	@Override
	public Libro eliminarLibro(Long id) {
		Libro lib = libroRepo.findOne(id);
		if(lib == null){
			throw new RuntimeException("Libro no existe");
		}
		lib.getAutor().iterator();
		libroRepo.delete(lib);
		return lib;
	}

	@Override
	public Optional<Libro> buscarPorId(Long id) {
		return Optional.ofNullable(libroRepo.findOne(id));
	}
}
