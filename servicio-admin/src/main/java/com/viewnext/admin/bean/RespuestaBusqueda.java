package com.viewnext.admin.bean;

import java.util.ArrayList;
import java.util.List;

public class RespuestaBusqueda {

	private List<BusquedaLibro> libros = new ArrayList<>();
	private int totalPaginas;
	private int numPaginaActual;
	private long numTotalLibros;
	private int tamanoPagina;

	public List<BusquedaLibro> getLibros() {
		return libros;
	}

	public void setLibros(List<BusquedaLibro> libros) {
		this.libros = libros;
	}

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public int getNumPaginaActual() {
		return numPaginaActual;
	}

	public void setNumPaginaActual(int numPaginaActual) {
		this.numPaginaActual = numPaginaActual;
	}

	public long getNumTotalLibros() {
		return numTotalLibros;
	}

	public void setNumTotalLibros(long numTotalLibros) {
		this.numTotalLibros = numTotalLibros;
	}

	public int getTamanoPagina() {
		return tamanoPagina;
	}

	public void setTamanoPagina(int tamanoPagina) {
		this.tamanoPagina = tamanoPagina;
	}
	
	
	
}
