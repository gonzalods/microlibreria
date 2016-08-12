package com.viewnext.carrito.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.viewnext.carrito.entity.Carrito;

@Repository
public interface CarritoRepositorio extends CrudRepository<Carrito, String>{

}
