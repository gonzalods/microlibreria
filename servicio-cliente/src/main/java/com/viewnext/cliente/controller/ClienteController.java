package com.viewnext.cliente.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.cliente.entity.Cliente;
import com.viewnext.cliente.exception.ClienteNoExistenteException;
import com.viewnext.cliente.exception.NombreusuarioYaExisteException;
import com.viewnext.cliente.servicio.ClienteServicio;

@RestController
@RequestMapping("/cliente")
//@CrossOrigin(origins="*", allowedHeaders = {"x-auth-token", "x-requested-with","x-xsrf-token"})
public class ClienteController {
	
	@Autowired
	private ClienteServicio cliServicio;
	
	@RequestMapping(method=RequestMethod.POST)
	public HttpEntity<?> crearCliente(@RequestBody Cliente cliente){
		 try{
			 Cliente nuevo = cliServicio.guardarCliente(cliente); 
			 return new ResponseEntity<Cliente>(nuevo, HttpStatus.CREATED);
		 }catch(NombreusuarioYaExisteException e){
			 return new ResponseEntity<String>("Nombre usuario ya existe", HttpStatus.BAD_REQUEST);
		 }
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	//@CrossOrigin(allowedHeaders = {"content-type"})
	public HttpEntity<?> actualizarCliente(@RequestBody Cliente cliente){
		try{
			 Cliente actualizado = cliServicio.actualizarCliente(cliente); 
			 return new ResponseEntity<Cliente>(actualizado, HttpStatus.OK);
//		 }catch(NombreusuarioYaExisteException e){
//			 return new ResponseEntity<String>("Nombre usuario ya existe", HttpStatus.BAD_REQUEST);
		 }catch (ClienteNoExistenteException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public HttpEntity<?> eliminarCliente(@PathVariable Long id){
		try{
			 Cliente borrado = cliServicio.eliminarCliente(id); 
			 return new ResponseEntity<Cliente>(borrado, HttpStatus.OK);
		 }catch (ClienteNoExistenteException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/{nombreusuario}", method=RequestMethod.GET)
	public HttpEntity<?> obtenerCliente(@PathVariable String nombreusuario){
		Optional<Cliente> cliente = cliServicio.buscarPorNombreusuario(nombreusuario);
		if(cliente.isPresent()){
			return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
