package com.viewnext.microlibreria.catalogo.repositorio;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viewnext.microlibreria.catalogo.entity.Libro;

@Repository
@Lazy
public interface LibroRepositorio extends JpaRepository<Libro, Long> {

}
