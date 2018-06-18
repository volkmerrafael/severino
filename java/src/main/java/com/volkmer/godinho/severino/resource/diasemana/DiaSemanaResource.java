package com.volkmer.godinho.severino.resource.diasemana;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.DiaSemana;

public class DiaSemanaResource extends ResourceCRUD<DiaSemana> {
	
	public DiaSemanaResource() {
	}
	
	@Override
	public Class<DiaSemana> getModelClass() {
		return DiaSemana.class;
	}
	
	public DiaSemana buscaPorNome(String nomeDiaSemana) throws Exception {
		
		TypedQuery<DiaSemana> queryDiaSemana = this.getEm().createQuery("select d from DiaSemana d where d.nome = :nomeDiaSemana", DiaSemana.class);
		queryDiaSemana.setParameter("nomeDiaSemana", nomeDiaSemana);	
		
		DiaSemana daisemana = new DiaSemana();
		
		try {
			daisemana = queryDiaSemana.getSingleResult();
		} catch (NoResultException e) {
			daisemana = null;
		}
		
		return daisemana;
		
	}

}