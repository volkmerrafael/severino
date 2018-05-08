package com.volkmer.godinho.severino.resource.acesso;

import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.Acesso;

public class AcessoResource extends ResourceCRUD<Acesso> {

	public AcessoResource() {
	}
	
	public AcessoResource(ResourceCRUD<?> res) {
		super(res);
	}
	
	@Override
	public Class<Acesso> getModelClass() {
		return Acesso.class;
	}

	public Acesso buscaPorNomeDeAcesso(String nomeacesso) {
		
		nomeacesso = nomeacesso.trim().toLowerCase();
		
		TypedQuery<Acesso> query = this.getEm().createQuery("select a from Acesso a where a.nomeacesso = :nomeacesso", this.getModelClass());
		query.setParameter("nomeacesso", nomeacesso);
		
		try {
			Acesso acesso = query.getSingleResult();
			return acesso;
		} catch (Exception e) {
			if (e.getMessage().indexOf("No entity found for query")==-1) {
				e.printStackTrace();
			}
		}
		
		return null;
		
	}

	public Acesso buscaPorToken(String token) {
		
		TypedQuery<Acesso> query = this.getEm().createQuery("select a from Acesso a where a.token = :token", this.getModelClass());
		query.setParameter("token", token);
		Acesso acesso = query.getSingleResult();
		return acesso;
		
	}
	
	public Acesso buscaPorUsuario(Long id) {
		
		TypedQuery<Acesso> query = this.getEm().createQuery("select a from Usaurio u inner join u.acesso a  where u.id = :id", this.getModelClass());
		query.setParameter("id", id);
		Acesso acesso = query.getSingleResult();
		return acesso;
		
	}
	
}
