package br.com.controleestoque.repository.ControleEstoqueBean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.com.controleestoque.repository.filter.FabricanteFilter;
import br.com.controleestoqueBean.Fabricante;

public class FabricanteQueryImpl implements FabricanteQuery {


	@PersistenceContext
	private EntityManager manager;

	@Override
	public Fabricante filtrar(FabricanteFilter controleEstoqueBeanFilter) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Fabricante> criteria = builder.createQuery(Fabricante.class);
		Root<Fabricante> root = criteria.from(Fabricante.class);

		Predicate[] predicates = criarRestricoes(controleEstoqueBeanFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Fabricante> query = manager.createQuery(criteria);

		return query.getSingleResult();
	}



	private Predicate[] criarRestricoes(FabricanteFilter controleEstoqueBeanFilter, CriteriaBuilder builder,
			Root<Fabricante> root) {
		
		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(controleEstoqueBeanFilter.getNomeFabricante())) {
			predicates.add(builder.like(
					builder.lower(root.get("nomeFabricante")), "%" + controleEstoqueBeanFilter.getNomeFabricante().toLowerCase() + "%"));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}






}
