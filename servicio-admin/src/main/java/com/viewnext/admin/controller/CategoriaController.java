package com.viewnext.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.viewnext.admin.bean.Categoria;
import com.viewnext.admin.servicio.CatalogoServicio;

//@Controller
@RequestMapping("/categoria")
public class CategoriaController {

	@Autowired
	private CatalogoServicio catalogoServicio;
	
	@ModelAttribute("categorias")
	public List<Categoria> categorias(){
		return catalogoServicio.todasCategorias();
	}
	
	@GetMapping()
	public String gestion(){
		return "categorias";
	}
	
	@PostMapping("/actualizar")
	public String actualizar(Categoria cat, Model model){
		catalogoServicio.actualizarCategoria(cat);
		model.addAttribute("msg", "Categoría actualizada correctamente");
		return "categorias";
	}
	
	@PostMapping("/nueva")
	public String nueva(Categoria cat, Model model){
		catalogoServicio.nuevaCategoria(cat);
		model.addAttribute("msg", "Categoría creada correctamente");
		return "categorias";
	}
	
	@GetMapping("/eliminar")
	public String eliminar(Long id, Model model){
		catalogoServicio.eliminarCategoria(id);
		model.addAttribute("msg", "Categoría eliminada correctamente");
		return "categorias";
	}
}
