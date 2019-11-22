package br.com.controleestoque.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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

import br.com.controleestoque.repository.ProdutoRepository;
import br.com.controleestoque.repository.filter.ProdutoFilter;
import br.com.controleestoque.repository.filter.ProdutoQuatidadeFilter;
import br.com.controleestoqueBean.Produto;
import io.swagger.annotations.ApiOperation;
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
	
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProdutoRepository produtoRepository;

	@ApiOperation(value = "Lista todos produtos")
	@GetMapping("/todos")
	public List<Produto> listar() {
		return produtoRepository.findAll();
	}

	@ApiOperation(value = "Salva produto")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void criar(@RequestBody @Valid Produto produto) {
		produtoRepository.save(produto);
	}

	@ApiOperation(value = "Deleta produto")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("codigo")Long codigo) {
		produtoRepository.deleteById(codigo);
	}
	@ApiOperation(value = "Altera produto")
	@PutMapping("/{codigo}")
	public ResponseEntity<Produto>atualizar(@PathVariable Long codigo,@Valid @RequestBody Produto produto ){
		
		Optional<Produto> produtoSalva = produtoRepository.findById(codigo);
		
		BeanUtils.copyProperties(produto, produtoSalva.get(), "codigo");
		
		produtoRepository.save(produtoSalva.get());
		
		return ResponseEntity.ok(produtoSalva.get());
		
	}
	
	//avaliar o metodo filtrar
	@ApiOperation(value = "Filtra produto")
	@GetMapping
	public ResponseEntity<Produto> buscarPorDescricao(ProdutoFilter produtoFilter){
		Produto filtrado = produtoRepository.filtrar(produtoFilter);
		return ResponseEntity.ok(filtrado);
	}


	@ApiOperation(value = "Baixa no estoque")
	@PutMapping("/{codigo}/quantidade")
	public ResponseEntity<?> baixaEstoque(
			@PathVariable("codigo") Long codigo, @RequestBody Long quantidade
		  )  {
	
		Produto produtoSalva = produtoRepository.findById(codigo).orElse(null);
		
		if(produtoRepository.quantidadeEstoque(codigo) - quantidade <=0) {
			return ResponseEntity.badRequest().body(new ProdutoQuatidadeFilter());
		}
		
		produtoSalva.setQuantidade(produtoRepository.quantidadeEstoque(codigo) - quantidade);
		
		produtoRepository.save(produtoSalva);
		
		 return ResponseEntity.ok(produtoSalva);
	}
	
	
}
