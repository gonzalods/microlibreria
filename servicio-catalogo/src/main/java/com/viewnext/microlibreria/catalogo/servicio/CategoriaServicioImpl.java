package com.viewnext.microlibreria.catalogo.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viewnext.microlibreria.catalogo.entity.Categoria;
import com.viewnext.microlibreria.catalogo.repositorio.CategoriaRepositorio;

@Service
public class CategoriaServicioImpl implements CategoriaServicio {

	private CategoriaRepositorio catRepo;
	
	@Autowired
	public CategoriaServicioImpl(CategoriaRepositorio catRepo) {
		this.catRepo = catRepo;
	}

	@Override
	public Categoria guardaCategoria(Categoria categoria) {
		return catRepo.save(categoria);
	}

	@Override
	public Categoria actualizarCategoria(Categoria categoria) {
		return catRepo.save(categoria);
	}

	@Override
	public Categoria eliminarCategoria(Long id) {
		Categoria cat = catRepo.findOne(id);
		catRepo.delete(id);
		return cat;
	}

	@Override
	public Optional<Categoria> buscarPorId(Long id) {
		return Optional.ofNullable(catRepo.findOne(id));
	}

	@Override
	public List<Categoria> todasCategorias() {
		return catRepo.findAll();
	}

}
