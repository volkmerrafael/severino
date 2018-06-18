package com.volkmer.godinho.severino.resource.departamento;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.Departamento;

public class DepartamentoResource extends ResourceCRUD<Departamento> {
	
	public DepartamentoResource() {
	}
	
	@Override
	public Class<Departamento> getModelClass() {
		return Departamento.class;
	}
	
	public Departamento buscaPorNome(String nomeDepartamento) throws Exception {
		
		TypedQuery<Departamento> queryDepartamento = this.getEm().createQuery("select d from Departamento d where d.nome = :nomeDepartamento", Departamento.class);
		queryDepartamento.setParameter("nomeDepartamento", nomeDepartamento);	
		
		Departamento departamento = new Departamento();
		
		try {
			departamento = queryDepartamento.getSingleResult();
		} catch (NoResultException e) {
			departamento = null;
		}
		
		return departamento;
		
	}
	
}