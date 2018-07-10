package com.volkmer.godinho.severino.resource.mod_estoque.produto;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.mod_estoque.Produto;

public class ProdutoResource extends ResourceCRUD<Produto> {

	@Override
	public Class<Produto> getModelClass() {
		return Produto.class;
	}

	public Produto getProdutoByDescricao(String descricao) {
		
		TypedQuery<Produto> queryProduto = this.getEm().createQuery(" select p from Produto p where p.descricao like :descricao", Produto.class);
		queryProduto.setParameter("descricao", descricao);
		
		Produto produto = new Produto();
		
		try {
			produto = queryProduto.getSingleResult();
		} catch (NoResultException e) {
			produto = null;
		}
		
		return produto;
		
	}

}
