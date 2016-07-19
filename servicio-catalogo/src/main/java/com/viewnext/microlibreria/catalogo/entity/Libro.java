package com.viewnext.microlibreria.catalogo.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CAT_LIBRO")
public class Libro {

	@Id
	@GeneratedValue
	private Long id;
	private String titulo;
	private String descripcion;
	private BigDecimal precio;
	private Integer anno;
	@ElementCollection
	@CollectionTable(name="CAT_AUTORES",
		joinColumns=@JoinColumn(name="ID_LIBRO"))
	@Column(name="NOMBRE")	
	private Set<String> autores;
	private String isbn;
	@OneToOne
	@JoinColumn(name="CATEGORIA")
	private Categoria categoria;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public Integer getAnno() {
		return anno;
	}
	public void setAnno(Integer anno) {
		this.anno = anno;
	}
	public Set<String> getAutor() {
		return autores;
	}
	public void setAutor(Set<String> autores) {
		this.autores = autores;
	}
	public void addAutor(String autor){
		if(autores==null){
			autores = new HashSet<>();
		}
		autores.add(autor);
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
