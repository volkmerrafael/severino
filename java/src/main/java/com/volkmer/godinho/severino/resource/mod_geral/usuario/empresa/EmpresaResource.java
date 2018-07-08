package com.volkmer.godinho.severino.resource.mod_geral.usuario.empresa;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Empresa;

public class EmpresaResource extends ResourceCRUD<Empresa> {
	
	public EmpresaResource() {
	}
	
	@Override
	public Class<Empresa> getModelClass() {
		return Empresa.class;
	}
	
	public Empresa buscaPorRazaoSocial(String razao_social) throws Exception {
		
		TypedQuery<Empresa> queryEmpresa = this.getEm().createQuery("select e from Empresa e where e.razao_social = :razao_social", Empresa.class);
		queryEmpresa.setParameter("razao_social", razao_social);	
		
		Empresa empresa = new Empresa();
		
		try {
			empresa = queryEmpresa.getSingleResult();
		} catch (NoResultException e) {
			empresa = null;
		}
		
		return empresa;
		
	}
	
}