package com.volkmer.godinho.severino.resource.justificativa;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.Acesso;
import com.volkmer.godinho.severino.entity.Justificativa;
import com.volkmer.godinho.severino.entity.Usuario;

public class JustificativaResource extends ResourceCRUD<Justificativa> {
	
	public JustificativaResource() {
	}
	
	@Override
	public Class<Justificativa> getModelClass() {
		return Justificativa.class;
	}

	public List<Justificativa> listarJustificativa(String token, Integer ano, Integer mes) {
		
		TypedQuery<Justificativa> queryJustificativa = this.getEm().createQuery("select p from Justificativa p where p.usuario = :usuario and Month(p.data) = :mes and Year(p.data) = :ano order by p.data asc", Justificativa.class);
		queryJustificativa.setParameter("usuario", this.buscaUsuarioPeloToken(token));
		queryJustificativa.setParameter("mes", mes);
		queryJustificativa.setParameter("ano", ano);
	
		List<Justificativa> lista = queryJustificativa.getResultList();
		
		return lista;

	}
	
	private Usuario buscaUsuarioPeloToken(String token) {
		
		//Consulta token para buscar qual usuário esta requisitando o ponto
		TypedQuery<Acesso> queryAcesso = this.getEm().createQuery("select a from Acesso a where a.token = :token", Acesso.class);
		queryAcesso.setParameter("token", token);
		Acesso acesso = queryAcesso.getSingleResult();
		
		//Consulta usuario para mostrar somente informações do mesmo
		TypedQuery<Usuario> queryUsuario = this.getEm().createQuery("select u from Usuario u where u.acesso = :acesso", Usuario.class);
		queryUsuario.setParameter("acesso", acesso);
		Usuario usuario = queryUsuario.getSingleResult();
	
		return usuario;
		
	}

	public Justificativa justificativaPorData(String token, LocalDate data) {

		TypedQuery<Justificativa> queryJustificativa = this.getEm().createQuery("select p from Justificativa p where p.usuario = :usuario and data = :data", Justificativa.class);
		queryJustificativa.setParameter("usuario", this.buscaUsuarioPeloToken(token));
		queryJustificativa.setParameter("data", data);
	
		List<Justificativa> lista = queryJustificativa.getResultList();
		
		return lista.get(0);

	}
}
