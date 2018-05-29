package com.volkmer.godinho.severino.resource.controlehoras;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.Acesso;
import com.volkmer.godinho.severino.entity.AnoMes;
import com.volkmer.godinho.severino.entity.ControleHoras;
import com.volkmer.godinho.severino.entity.Ponto;
import com.volkmer.godinho.severino.entity.Usuario;

public class ControleHorasResource extends ResourceCRUD<Ponto> {
	
	public ControleHorasResource() {
	}
	
	@Override
	public Class<Ponto> getModelClass() {
		return Ponto.class;
	}
	
	public ControleHoras listarControleHoras(String token, Integer ano, Integer mes) {
	
		TypedQuery<AnoMes> queryAnoMes = this.getEm().createQuery("select p from AnoMes p where mes = :mes and ano = :ano", AnoMes.class);
		queryAnoMes.setParameter("mes", mes);
		queryAnoMes.setParameter("ano", ano);
		AnoMes anomes = queryAnoMes.getSingleResult();
		
		//Usuário encontrado agora retorna a lista de ponto do usuário
		TypedQuery<Ponto> queryPonto = this.getEm().createQuery("select p from Ponto p where p.usuario = :usuario and Month(p.data) = :mes and Year(p.data) = :ano order by p.data asc", Ponto.class);
		queryPonto.setParameter("usuario", this.buscaUsuarioPeloToken(token));
		queryPonto.setParameter("mes", mes);
		queryPonto.setParameter("ano", ano);
		List<Ponto> lista = queryPonto.getResultList();
			
		Integer minutosCredito = 0;
		Integer minutosDebito = 0;
		
		for (Ponto ponto : lista) {
			if (ponto.getMinutos_credito()!=null) {
				minutosCredito += ponto.getMinutos_credito();
			} else if (ponto.getMinutos_credito()!=null) {
				minutosDebito += ponto.getMinutos_debito();
			}
		}
		
		Integer horac = minutosCredito/60;
		Integer minutoc = minutosCredito%60;
		LocalTime lc = LocalTime.of(horac, minutoc, 0, 0);
		lc.plusMinutes(minutoc);
		
		Integer horad = minutosDebito/60;
		Integer minutod = minutosDebito%60;
		LocalTime ld = LocalTime.of(horad, minutod, 0, 0);
		
		ControleHoras ch = new ControleHoras();
		ch.setAnomes(anomes);
		ch.setHoras_credito(lc);
		ch.setHoras_debito(ld);
		//ch.setBanco_de_horas(lc.(ld));
		
		return ch;
		
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