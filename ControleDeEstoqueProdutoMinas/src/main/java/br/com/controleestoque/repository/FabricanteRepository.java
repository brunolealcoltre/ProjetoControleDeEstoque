package br.com.controleestoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.controleestoque.repository.ControleEstoqueBean.FabricanteQuery;
import br.com.controleestoqueBean.Fabricante;

public interface FabricanteRepository extends JpaRepository<Fabricante, Long>, FabricanteQuery {
	
	
}
