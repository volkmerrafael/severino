package com.volkmer.godinho.severino.resource.mod_controleponto.legenda;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.core.rest.filters.RestException;
import com.volkmer.godinho.severino.entity.mod_controleponto.Legenda;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.resource.mod_acesso.acesso.AcessoTipo;
import com.volkmer.godinho.severino.resource.mod_geral.usuario.UsuarioResource;

public class LegendaResource extends ResourceCRUD<Legenda> {
	
	public LegendaResource() {
	}
	
	@Override
	public Class<Legenda> getModelClass() {
		return Legenda.class;
	}
	
	private RestException erroUsuarioNaoPertenceAoCoordenadorLogado = new RestException("Usuário Não pertence ao coordenador logado.");
	
	public List<Legenda> listarLegenda(Long usuarioid, String token, Integer ano, Integer mes) throws Exception {
		
		Usuario usuario = new Usuario();
		Usuario usuariodoid =  new Usuario();
		
		try (UsuarioResource usuRes = new UsuarioResource()) {
			
			usuario = usuRes.buscaUsuarioPeloToken(token);
			usuariodoid = usuRes.buscaUsuarioPeloId(usuarioid);
			
			TypedQuery<Legenda> queryLegenda = this.getEm().createQuery("select l from Legenda l where l.id in (select p.legenda from Ponto p where p.usuario = :usuario and Month(p.data) = :mes and Year(p.data) = :ano))", Legenda.class);
			
			if (usuario.getAcesso().getTipo().equals(AcessoTipo.NORMAL)) {
				queryLegenda.setParameter("usuario", usuario);
			} else if (usuario.getAcesso().getTipo().equals(AcessoTipo.COORDENADOR)) {
				if (usuariodoid.getDepartamento().getId().equals(usuario.getDepartamento().getId())) {
					queryLegenda.setParameter("usuario", usuariodoid);
				} else {
					throw erroUsuarioNaoPertenceAoCoordenadorLogado;
				}
			}
			
			queryLegenda.setParameter("usuario", usuario);
			queryLegenda.setParameter("mes", mes);
			queryLegenda.setParameter("ano", ano);
			List<Legenda> lista = queryLegenda.getResultList();
			
			return lista;
			
		} catch (NoResultException e) {
		}
		
		return null;
		
	}
	
	public Legenda buscaPorSigla(String sigla) throws Exception {
		
		TypedQuery<Legenda> queryLegenda = this.getEm().createQuery("select d from Legenda d where d.sigla = :sigla", Legenda.class);
		queryLegenda.setParameter("sigla", sigla);	
		
		Legenda legenda = new Legenda();
		
		try {
			legenda = queryLegenda.getSingleResult();
		} catch (NoResultException e) {
			legenda = null;
		}
		
		return legenda;
		
	}
	
}