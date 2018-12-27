package br.com.controleestoque.repository.ControleEstoqueBean;

import br.com.controleestoque.repository.filter.ProdutoFilter;
import br.com.controleestoqueBean.Produto;

public interface ProdutoQuery {

	public Produto filtrar(ProdutoFilter produtoFilter);
	
}
