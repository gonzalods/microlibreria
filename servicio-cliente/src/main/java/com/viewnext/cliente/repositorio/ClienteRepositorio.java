package com.viewnext.cliente.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viewnext.cliente.entity.Cliente;

 @Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

	 public Optional<Cliente> findByNombreusuario(String nombreusuario);
}
