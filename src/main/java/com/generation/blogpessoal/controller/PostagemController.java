package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/postagens") // indica o endereço de onde vem a requisição. todos os metodos desta classe herdarão deste endereço
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {//Classe que se comunica com o Client / Front e passa para o Repository interface
	
	@Autowired // Inversão de dependencia da interface - vira autonoma 
	private PostagemRepository postagemRepository; //postagemRepository é o atributo/objeto que invoca os métodos ja implementados no spring 
	
	@Autowired
	private TemaRepository temaRepository;
	
	@GetMapping // postagens - Requisições do tipo GET
	public ResponseEntity<List<Postagem>> getAll(){ //ResponseEntity é a resposta que o Insomnia deve fazer 
		return ResponseEntity.ok(postagemRepository.findAll());	
	}
	
	@GetMapping("/{id}") // id é o endereço {} indica que o id é um valor = numero
	public ResponseEntity<Postagem> getById(@PathVariable Long id){ // pathvariable é o caminho que pega o id 
		
		return postagemRepository.findById(id) // Métodos de manipulação de dados em SQL ja tão prontos 
				.map(resposta -> ResponseEntity.ok(resposta)) // .map é um Optional, resp : objeto que esta dentro do Optional, ResponseEntity.ok = 200ok
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());                                                                             
	}
	
	@GetMapping("/titulo/{titulo}") // BUSCAR /postagens/titulo/algum_texto 
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping // POSTAR- CRIAR / postagens  && Verbo HTTP for Post 
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem){ // Request body indica que vem do corpo da requisição Body Json no Insomnia
		
		if (temaRepository.existsById(postagem.getTema().getId())) { //Se tema não existir exibira reposta 
		
			postagem.setId(null); // acessa o objeto, id e deixa como nulo 
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(postagemRepository.save(postagem));
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe!", null); //Se tema não existir exibira reposta 
	}
		
	
	@PutMapping
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem) {

		if (postagemRepository.existsById(postagem.getId())) {

			if (temaRepository.existsById(postagem.getTema().getId()))
				return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe!", null);

		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}") // DELETAR
	public void delete(@PathVariable Long id) {
		Optional<Postagem> postagem = postagemRepository.findById(id); //Optional <> nome = classe.metodo
		
		if(postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		postagemRepository.deleteById(id);
		
	}
}
