package com.volkmer.godinho.severino.resource.jornada;

import java.util.List;

import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.Acesso;
import com.volkmer.godinho.severino.entity.Jornada;
import com.volkmer.godinho.severino.entity.Usuario;

public class JornadaResource extends ResourceCRUD<Jornada> {
	
	public JornadaResource() {
	}
	
	@Override
	public Class<Jornada> getModelClass() {
		return Jornada.class;
	}
	
	public List<Jornada> listarJornada(String token, Integer ano, Integer mes) {
		
		//Jornada encontrado agora retorna a lista de ponto do usuário
		TypedQuery<Jornada> queryJornada = this.getEm().createQuery("select j from Jornada j where j.id in (select p.jornada from Ponto p where p.usuario = :usuario and Month(p.data) = :mes and Year(p.data) = :ano))", Jornada.class);
		queryJornada.setParameter("usuario", this.buscaUsuarioPeloToken(token));
		queryJornada.setParameter("mes", mes);
		queryJornada.setParameter("ano", ano);
		List<Jornada> lista = queryJornada.getResultList();
		
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
}