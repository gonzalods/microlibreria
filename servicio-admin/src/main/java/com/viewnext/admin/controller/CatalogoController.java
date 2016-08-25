package com.viewnext.admin.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.viewnext.admin.bean.Categoria;
import com.viewnext.admin.bean.FiltroBusqueda;
import com.viewnext.admin.bean.Libro;
import com.viewnext.admin.bean.RespuestaBusqueda;
import com.viewnext.admin.security.SecurityTokens;
import com.viewnext.admin.security.SessionTokens;
import com.viewnext.admin.servicio.CatalogoServicio;

@Controller
public class CatalogoController {
	
	@Autowired
	private CatalogoServicio catalogoServicio;
	
	@ModelAttribute("categorias")
	public List<Categoria> categorias(){
		return catalogoServicio.todasCategorias();
	}
	
	@GetMapping("/catalogo")
	public String catalogo(@ModelAttribute("filtro") FiltroBusqueda filtro , 
			@ModelAttribute("categorias") List<Categoria> categorias,Model model){
		if(categorias.isEmpty()){
			model.addAttribute("sevicionodisponible", true);
			return "index";
		}else{
			model.addAttribute("sevicionodisponible", false);
			return "catalogo";
		}
	}
	
	@PostMapping("/busqueda")
	public String busqueda(@ModelAttribute("filtro") FiltroBusqueda filtro, Model model){
		RespuestaBusqueda respuesta = catalogoServicio.buscarPorCriterios(filtro);
		if(respuesta == null){
			model.addAttribute("servicionodisponible", true);
		}else{
			model.addAttribute("servicionodisponible", false);
			model.addAttribute("libros", respuesta.getLibros());
		}
		return "catalogo";
	}
	
	@GetMapping("/detalle")
	public String detalle(@RequestParam("id") Long id, @ModelAttribute("filtro") FiltroBusqueda filtro, Model model){
		
		Libro libro = catalogoServicio.buscarLibroPorId(id);
		model.addAttribute("libro", libro);
		return "catalogo";
	}
	
	
	@PostMapping("/actualizar")
	public String actualizar(Libro libro, @RequestParam("busqueda")String busqueda, 
			@RequestParam("categoriabusq")Long categoriabusq, HttpSession session, Model model){
		SecurityTokens st = new SecurityTokens();
		st.setSession(session.getId());
		SessionTokens.set(st);
		catalogoServicio.actualizarLibro(libro);
		FiltroBusqueda filtro = new FiltroBusqueda();
		filtro.setBusqueda(busqueda);
		filtro.setCategoria(categoriabusq);
		model.addAttribute("filtro", filtro);
		model.addAttribute("msg", "Registro actualizado correctamente");
		return "catalogo";
	}

	@GetMapping("/nuevoLibro")
	public String nuevo(@ModelAttribute("libro") Libro libro,
			@ModelAttribute("categorias") List<Categoria> categorias,Model model){
		
		if(categorias.isEmpty()){
			model.addAttribute("sevicionodisponible", true);
			return "index";
		}else{
			model.addAttribute("sevicionodisponible", false);
			return "nuevoLibro";
		}
	}
	
	@PostMapping("/nuevo")
	public String insertar(@ModelAttribute("libro")Libro libro, Model model){
		catalogoServicio.nuevoLibro(libro);
		model.addAttribute("msg", "Libro creado correctamente");
		return "nuevoLibro";
	}
	
	@GetMapping("/eliminar")
	public String eliminar(@RequestParam("id") Long id, @ModelAttribute("filtro") FiltroBusqueda filtro, Model model){
		catalogoServicio.eliminarLibro(id);
		RespuestaBusqueda respuesta = catalogoServicio.buscarPorCriterios(filtro);
		model.addAttribute("libros", respuesta.getLibros());
		model.addAttribute("filtro", filtro);
		model.addAttribute("msg", "Libro eliminado correctamente");
		return "catalogo";
	}
	
}
