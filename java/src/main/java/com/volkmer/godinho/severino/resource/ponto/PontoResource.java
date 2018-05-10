package com.volkmer.godinho.severino.resource.ponto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.Acesso;
import com.volkmer.godinho.severino.entity.AnoMes;
import com.volkmer.godinho.severino.entity.Ponto;
import com.volkmer.godinho.severino.entity.Usuario;

public class PontoResource extends ResourceCRUD<Ponto> {
	
	public PontoResource() {
	}
	
	@Override
	public Class<Ponto> getModelClass() {
		return Ponto.class;
	}
	
	public List<Ponto> listarPontos(String token, Integer ano, Integer mes) {
	
		//Usuário encontrado agora retorna a lista de ponto do usuário
		TypedQuery<Ponto> queryPonto = this.getEm().createQuery("select p from Ponto p where p.usuario = :usuario and Month(p.data) = :mes and Year(p.data) = :ano", Ponto.class);
		queryPonto.setParameter("usuario", this.buscaUsuarioPeloToken(token));
		queryPonto.setParameter("mes", mes);
		queryPonto.setParameter("ano", ano);
		List<Ponto> lista = queryPonto.getResultList();
				
		return lista;
		
	}

	public List<AnoMes> listarPeriodos(String token) {
		
		//Lista Mês e Ano em que o usuário tem ponto lançado
		TypedQuery<Ponto> queryPonto = this.getEm().createQuery("select p from Ponto p where p.usuario = :usuario", Ponto.class);
		queryPonto.setParameter("usuario", this.buscaUsuarioPeloToken(token));
		List<Ponto> lista = queryPonto.getResultList();
				
		List<AnoMes> listaAnoMes = new ArrayList<AnoMes>();
		
		for (Ponto ponto : lista) {
			
			if (!listaAnoMes.contains(ponto.getAnomes())) {
				listaAnoMes.add(ponto.getAnomes());
			}
				
		}
		
		return listaAnoMes;
		
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