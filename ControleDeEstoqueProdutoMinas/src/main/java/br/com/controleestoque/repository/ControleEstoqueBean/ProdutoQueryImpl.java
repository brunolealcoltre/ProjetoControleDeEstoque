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

import br.com.controleestoque.repository.filter.ProdutoFilter;
import br.com.controleestoqueBean.Produto;

public class ProdutoQueryImpl implements ProdutoQuery {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Produto filtrar (ProdutoFilter produtoFilter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteria = builder.createQuery(Produto.class);
		Root<Produto> root = criteria.from(Produto.class);

		Predicate[] predicates = criarRestricoes(produtoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Produto> query = manager.createQuery(criteria);
		return query.getSingleResult();
	}

	private Predicate[] criarRestricoes(ProdutoFilter produtoFilter, CriteriaBuilder builder, Root<Produto> root) {

		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(produtoFilter.getNomeProduto())) {
			predicates.add(builder.like(builder.lower(root.get("nomeProduto")),
					"%" + produtoFilter.getNomeProduto().toLowerCase() + "%"));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
