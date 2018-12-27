package br.com.controleestoque.repository.ControleEstoqueBean;

import br.com.controleestoque.repository.filter.FabricanteFilter;
import br.com.controleestoqueBean.Fabricante;

public interface FabricanteQuery {

	public Fabricante filtrar(FabricanteFilter controleEstoqueBeanFilter);
	
}
