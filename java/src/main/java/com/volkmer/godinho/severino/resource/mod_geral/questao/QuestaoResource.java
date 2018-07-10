package com.volkmer.godinho.severino.resource.mod_geral.questao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.mod_geral.Questao;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.resource.mod_geral.usuario.UsuarioResource;

public class QuestaoResource extends ResourceCRUD<Questao> {
	
	public QuestaoResource() {
	}
	
	public QuestaoResource(ResourceCRUD<?> res) {
		super(res);
	}
	
	@Override
	public Class<Questao> getModelClass() {
		return Questao.class;
	}
	
	@Override
	protected void incluirPre(Questao model) throws Exception {
		
		model.setData_hora(LocalDateTime.now());
		
	}

	public List<Questao> listarQuestoesUsuario(String userToken) throws Exception {
		
		try (UsuarioResource usuRes = new UsuarioResource()) {
			Usuario usuario = usuRes.buscaUsuarioPeloToken(userToken);
			
			TypedQuery<Questao> query = this.getEm().createQuery("select u from Questao u where u.usuario = :usuario", this.getModelClass());
			query.setParameter("usuario", usuario);
			return query.getResultList();
						
		} catch (NoResultException e) {
		}
		
		return null;
	}
	
}
