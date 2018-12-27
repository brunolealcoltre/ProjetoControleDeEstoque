package br.com.controleestoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.controleestoque.repository.ControleEstoqueBean.ProdutoQuery;
import br.com.controleestoqueBean.Produto;


public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoQuery {
	
	 @Query(value = "SELECT sum(quantidade) from Produto where codigo = ?1", nativeQuery = true)
	 Long quantidadeEstoque(Long codigo);
	
}

