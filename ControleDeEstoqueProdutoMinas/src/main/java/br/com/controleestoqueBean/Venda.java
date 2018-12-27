package br.com.controleestoqueBean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

public class Venda {
	
	private Long id;
	@ManyToOne
	private List<Long> codigoProd;

	@ManyToOne
	private List<Long> codFabri;
	
	
	
    @Column
	private double valor;
	
	 
}
