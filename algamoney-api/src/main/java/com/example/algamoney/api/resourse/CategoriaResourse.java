package com.example.algamoney.api.resourse;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

//restController ja converte o retorno para Json
@RestController
@RequestMapping("/categorias")
public class CategoriaResourse {
	
	//Injetando o repositorio onde ja existem alguns metodos
	//@Autowired pede para o spring achar uma implementação de categoriaRepositry e entregue aqui
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	//@GetMapping informa qual metodo que sera usado("/categoria")
	@GetMapping
	public List<Categoria>listar(){
		return categoriaRepository.findAll();
		
	}
	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria,HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(categoriaSalva.getCodigo()).toUri();
			response.setHeader("Location",uri.toASCIIString());	
			
			return ResponseEntity.created(uri).body(categoriaSalva);		
	}
	@GetMapping("/{codigo}")
	public Categoria buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Categoria> c = categoriaRepository.findById(codigo);
		return c.get();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)	
	public void remover(@PathVariable Long codigo) {
		categoriaRepository.deleteById(codigo);
	}
	
}
