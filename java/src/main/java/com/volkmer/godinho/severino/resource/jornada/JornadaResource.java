package com.volkmer.godinho.severino.resource.jornada;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.core.rest.filters.RestException;
import com.volkmer.godinho.severino.entity.Jornada;
import com.volkmer.godinho.severino.entity.Usuario;
import com.volkmer.godinho.severino.resource.acesso.AcessoTipo;
import com.volkmer.godinho.severino.resource.usuario.UsuarioResource;

public class JornadaResource extends ResourceCRUD<Jornada> {
	
	public JornadaResource() {
	}
	
	@Override
	public Class<Jornada> getModelClass() {
		return Jornada.class;
	}
	
	private RestException erroUsuarioNaoPertenceAoCoordenadorLogado = new RestException("Usuário Não pertence ao coordenador logado.");
	
	public List<Jornada> listarJornada(Long usuarioid, String token, Integer ano, Integer mes) throws Exception {
		
		Usuario usuario = new Usuario();
		Usuario usuariodoid =  new Usuario();
		
		try (UsuarioResource usuRes = new UsuarioResource()) {
			
			usuario = usuRes.buscaUsuarioPeloToken(token);
			usuariodoid = usuRes.buscaUsuarioPeloId(usuarioid);
			
			//Jornada encontrado agora retorna a lista de ponto do usuário
			TypedQuery<Jornada> queryJornada = this.getEm().createQuery("select j from Jornada j where j.id in (select p.jornada from Ponto p where p.usuario = :usuario and Month(p.data) = :mes and Year(p.data) = :ano))", Jornada.class);
		
			if (usuario.getAcesso().getTipo().equals(AcessoTipo.NORMAL)) {
				queryJornada.setParameter("usuario", usuario);
			} else if (usuario.getAcesso().getTipo().equals(AcessoTipo.COORDENADOR)) {
				if (usuariodoid.getDepartamento().getId().equals(usuario.getDepartamento().getId())) {
					queryJornada.setParameter("usuario", usuariodoid);
				} else {
					throw erroUsuarioNaoPertenceAoCoordenadorLogado;
				}
			}
			
			queryJornada.setParameter("mes", mes);
			queryJornada.setParameter("ano", ano);
			List<Jornada> lista = queryJornada.getResultList();
			
			return lista;
			
		} catch (NoResultException e) {
		}
		
		return null;
	
	}
	
	public Jornada buscaPorPeriodoJornada(String periodo_jornada) throws Exception {
		
		TypedQuery<Jornada> queryJornada = this.getEm().createQuery("select u from Jornada u where u.periodo_jornada = :periodo_jornada", Jornada.class);
		queryJornada.setParameter("periodo_jornada", periodo_jornada);	
		
		Jornada jornada = new Jornada();
		
		try {
			jornada = queryJornada.getSingleResult();
		} catch (NoResultException e) {
			jornada = null;
		}
		
		return jornada;
		
	}
		
}