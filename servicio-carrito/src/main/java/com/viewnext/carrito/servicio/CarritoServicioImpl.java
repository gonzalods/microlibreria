package com.viewnext.carrito.servicio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viewnext.carrito.entity.Carrito;
import com.viewnext.carrito.entity.ItemCarrito;
import com.viewnext.carrito.repositorio.CarritoRepositorio;

@Service
public class CarritoServicioImpl implements CarritoServicio {

	private CarritoRepositorio carritoRepo;

	@Autowired
	public CarritoServicioImpl(CarritoRepositorio carritoRepo){
		this.carritoRepo = carritoRepo;
	}
	
	@Override
	public Integer anadirACarrito(String nombreusuario, ItemCarrito nuevo) {
		Carrito carrito = carritoRepo.findOne(nombreusuario);
		if(carrito == null){
			carrito = new Carrito();
			carrito.setNombreusuario(nombreusuario);
		}
		Map<String, ItemCarrito> mapItems = carrito.getItems();
		if(mapItems.containsKey(String.valueOf(nuevo.getId()))){
			ItemCarrito item = mapItems.get(String.valueOf(nuevo.getId()));
			item.setCantidad(item.getCantidad() + nuevo.getCantidad());
		}else{
			mapItems.put(String.valueOf(nuevo.getId()), nuevo);
		}
		carritoRepo.save(carrito);
		
		return cantidad(carrito.getItems());
	}

	@Override
	public List<ItemCarrito> eliminarDeCarrito(String nombreusuario, Long id) {
		List<ItemCarrito> items = new ArrayList<>();
		Carrito carrito = carritoRepo.findOne(nombreusuario);
		if(carrito == null){
			return Collections.emptyList();
		}
		Map<String, ItemCarrito> mapItems = carrito.getItems();
		if(mapItems.containsKey(String.valueOf(id))){
			mapItems.remove(String.valueOf(id));
		}
		if(mapItems.isEmpty()){
			carritoRepo.delete(nombreusuario);
		}else{
			carritoRepo.save(carrito);
			items.addAll(mapItems.values());
		}
		return Collections.unmodifiableList(items);
	}

	@Override
	public List<ItemCarrito> consultarCarrito(String nombreusuario) {
		List<ItemCarrito> items = new ArrayList<>();
		Carrito carrito = carritoRepo.findOne(nombreusuario);
		if(carrito != null){
			 items.addAll(carrito.getItems().values());
		}
		return Collections.unmodifiableList(items);
	}

	@Override
	public Integer cantidad(String nombreusuario) {
		Carrito carrito = carritoRepo.findOne(nombreusuario);
		if(carrito == null){
			return 0;
		}
		Map<String, ItemCarrito> mapItems = carrito.getItems();
		return cantidad(mapItems);
	}
	
	private Integer cantidad(Map<String, ItemCarrito> items){
		Collection<ItemCarrito> it = items.values();
		Integer numItems = 0;
		for(ItemCarrito item: it){
			numItems += item.getCantidad();
		}
		return numItems;
	}


}
