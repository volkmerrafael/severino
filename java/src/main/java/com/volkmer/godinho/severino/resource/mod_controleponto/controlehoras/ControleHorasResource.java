package com.volkmer.godinho.severino.resource.mod_controleponto.controlehoras;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.core.rest.filters.RestException;
import com.volkmer.godinho.severino.entity.mod_controleponto.AnoMes;
import com.volkmer.godinho.severino.entity.mod_controleponto.ControleHoras;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.resource.mod_acesso.acesso.AcessoTipo;
import com.volkmer.godinho.severino.resource.mod_geral.usuario.UsuarioResource;

public class ControleHorasResource extends ResourceCRUD<ControleHoras> {
	
	public ControleHorasResource() {
	}
	
	@Override
	public Class<ControleHoras> getModelClass() {
		return ControleHoras.class;
	}
	
	private RestException erroUsuarioNaoPertenceAoCoordenadorLogado = new RestException("Usuário Não pertence ao coordenador logado.");
	
	public ControleHoras listarControleHoras(Long usuarioid, String token, Integer ano, Integer mes) throws Exception {
		
		AnoMes anomes = null;
		ControleHoras controleHoras = null;
		Usuario usuario = new Usuario();
		Usuario usuariodoid =  new Usuario();
		
		TypedQuery<AnoMes> queryAnoMes = this.getEm().createQuery("select p from AnoMes p where mes = :mes and ano = :ano", AnoMes.class);
		queryAnoMes.setParameter("mes", mes);
		queryAnoMes.setParameter("ano", ano);
		
		try {
			anomes = queryAnoMes.getSingleResult();
			
			try (UsuarioResource usuRes = new UsuarioResource()) {

				usuario = usuRes.buscaUsuarioPeloToken(token);
				usuariodoid = usuRes.buscaUsuarioPeloId(usuarioid);
				
				TypedQuery<ControleHoras> queryControleHoras = this.getEm().createQuery("select p from ControleHoras p where anomes = :anomes and usuario = :usuario", ControleHoras.class);
				queryControleHoras.setParameter("anomes", anomes);
				
				if (usuario.getAcesso().getTipo().equals(AcessoTipo.NORMAL)) {
					queryControleHoras.setParameter("usuario", usuario);
				} else if (usuario.getAcesso().getTipo().equals(AcessoTipo.COORDENADOR)) {
					if (usuariodoid.getDepartamento().getId().equals(usuario.getDepartamento().getId())) {
						queryControleHoras.setParameter("usuario", usuariodoid);
					} else {
						throw erroUsuarioNaoPertenceAoCoordenadorLogado;
					}
				}
				
				controleHoras = queryControleHoras.getSingleResult();
				
			} catch (NoResultException e) {
			}
			
		} catch (NoResultException e) {
		}
		
		return controleHoras;
	}
	
	public List<ControleHoras> listarControleHorasAno(Long usuarioid, String token, Integer ano) throws Exception {
		
		List<ControleHoras> listaControleHoras = new ArrayList<ControleHoras>();
		
		TypedQuery<AnoMes> queryAnoMes = this.getEm().createQuery("select p from AnoMes p where ano = :ano", AnoMes.class);
		queryAnoMes.setParameter("ano", ano);
		
		List<AnoMes> listaAnoMes = new ArrayList<>();
		
		try {
			listaAnoMes = queryAnoMes.getResultList();
			
			for (AnoMes anoMes : listaAnoMes) {
				ControleHoras contHor = this.listarControleHoras(usuarioid, token, anoMes.getAno(), anoMes.getMes());
				
				if (contHor!=null) {
					listaControleHoras.add(contHor);
				}
			}
			
		} catch (NoResultException e) {
		}
		
		return listaControleHoras;
		
	}
	
	public ControleHoras buscaPorAnoMesEUsuario(AnoMes anomes, Usuario usuario) throws Exception {
		
		ControleHoras ch = new ControleHoras();
		
		TypedQuery<ControleHoras> queryControleHoras = this.getEm().createQuery("select p from ControleHoras p where p.anomes = :anomes and p.usuario = :usuario", ControleHoras.class);
		queryControleHoras.setParameter("anomes", anomes);
		queryControleHoras.setParameter("usuario", usuario);
		
		try {
			ch = queryControleHoras.getSingleResult();
			return ch;
		} catch (NoResultException e) {
		}
		
		return null;
		
	}

}