package com.volkmer.godinho.severino.resource.funcao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.Funcao;

public class FuncaoResource extends ResourceCRUD<Funcao> {
	
	public FuncaoResource() {
	}
	
	@Override
	public Class<Funcao> getModelClass() {
		return Funcao.class;
	}
	
	public Funcao buscaPorNome(String nomeFuncao) throws Exception {
		
		TypedQuery<Funcao> queryFuncao = this.getEm().createQuery("select d from Funcao d where d.nome = :nomeFuncao", Funcao.class);
		queryFuncao.setParameter("nomeFuncao", nomeFuncao);	
		
		Funcao funcao = new Funcao();
		
		try {
			funcao = queryFuncao.getSingleResult();
		} catch (NoResultException e) {
			funcao = null;
		}
		
		return funcao;
		
	}
}