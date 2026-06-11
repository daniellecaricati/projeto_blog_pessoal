package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.blogpessoal.model.Postagem;

//Classe que se comunica com o banco de dados, utiliza metodos da JPA - Interface padrao com metodos para interagir com banco de dados
public interface PostagemRepository extends JpaRepository<Postagem, Long> { 
	// indica como sera feita a pesquisa no banco de dados.
	public List <Postagem> findAllByTituloContainingIgnoreCase(String titulo); // metodo criado - publico tipo <nome> método (parametros); 
	// Titulo é o campo da tabela
}