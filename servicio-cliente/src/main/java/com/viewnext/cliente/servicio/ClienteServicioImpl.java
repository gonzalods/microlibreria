package com.viewnext.cliente.servicio;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viewnext.cliente.entity.Cliente;
import com.viewnext.cliente.exception.ClienteNoExistenteException;
import com.viewnext.cliente.exception.NombreusuarioYaExisteException;
import com.viewnext.cliente.repositorio.ClienteRepositorio;

@Service
public class ClienteServicioImpl implements ClienteServicio {

	private ClienteRepositorio cliRepo;
	
	@Autowired
	public ClienteServicioImpl(ClienteRepositorio cliRepo) {
		this.cliRepo = cliRepo;
	}

	@Override
	public Cliente guardarCliente(Cliente cliente) {
		cliente.setFechaAlta(new Date());
		Optional<Cliente> cli = buscarPorNombreusuario(cliente.getNombreusuario());
		if(cli.isPresent()){
			throw new NombreusuarioYaExisteException();
		}
		return cliRepo.save(cliente);
	}

	@Override
	public Cliente actualizarCliente(Cliente cliente) {
		if(!cliRepo.exists(cliente.getId())){
			throw new ClienteNoExistenteException();
		}
		return cliRepo.save(cliente);
	}

	@Override
	public Cliente eliminarCliente(Long id) {
		if(!cliRepo.exists(id)){
			throw new ClienteNoExistenteException();
		}
		Cliente cliente = cliRepo.findOne(id);
		cliRepo.delete(id);
		return cliente;
	}

	@Override
	public Optional<Cliente> buscarPorNombreusuario(String nombreusuario) {
		return cliRepo.findByNombreusuario(nombreusuario);
	}

}
