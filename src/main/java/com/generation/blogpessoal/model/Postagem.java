package com.generation.blogpessoal.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Anotações que servem para alterar ou definir comportamentos. São obrigatórias para conversar com o banco de dados.

@Entity //Define que a classe Postagem vai se tornar uma tabela
@Table(name = "tb_postagens") //serve para nomear a tabela 
public class Postagem {
	// Atributos sao privados, métodos sao publicos para que possam usar 
	
	@Id //Define a chave primaria 
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Define o campo para o Auto Increment 
	private Long id; //LONG = BIGINT
		
	@NotBlank(message = "O atributo título é obrigatório!") // not null
	@Size(min = 5, max = 100, message = "O atributo título deve ter no minimo 5 e no máximo 100 caracteres.") // tamanho 
	private String titulo; // Titulo tipo Varchar (100) Not Null 
	
	@NotBlank(message = "O atributo texto é obrigatório!")
	@Size(min = 10, max = 1000, message = "O atributo texto deve ter no minimo 10 e no máximo 1000 caracteres.")
	private String texto; // Texto tipo Varchar (100) Not Null 
	
	
	@UpdateTimestamp
	private LocalDateTime data; // colocar data e hora 
	
	@ManyToOne // Cria Relacionamento 
	@JsonIgnoreProperties("postagem")
	private Tema tema;
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;

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


	public String getTexto() {
		return texto;
	}


	public void setTexto(String texto) {
		this.texto = texto;
	}


	public LocalDateTime getData() {
		return data;
	}


	public void setData(LocalDateTime data) {
		this.data = data;
	}


	public Tema getTema() {
		return tema;
	}


	public void setTema(Tema tema) {
		this.tema = tema;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	

}
