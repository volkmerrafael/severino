package com.volkmer.godinho.severino.resource.justificativa;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.core.rest.filters.RestException;
import com.volkmer.godinho.severino.entity.Justificativa;
import com.volkmer.godinho.severino.entity.Usuario;
import com.volkmer.godinho.severino.resource.acesso.AcessoTipo;
import com.volkmer.godinho.severino.resource.usuario.UsuarioResource;

public class JustificativaResource extends ResourceCRUD<Justificativa> {
	
	public JustificativaResource() {
	}
	
	@Override
	public Class<Justificativa> getModelClass() {
		return Justificativa.class;
	}

	private RestException erroUsuarioNaoPertenceAoCoordenadorLogado = new RestException("Usuário Não pertence ao coordenador logado.");
	
	public List<Justificativa> listarJustificativa(Long usuarioid, String token, Integer ano, Integer mes) throws Exception {
		
		Usuario usuario = new Usuario();
		Usuario usuariodoid =  new Usuario();
		try (UsuarioResource usuRes = new UsuarioResource()) {
			
			usuario = usuRes.buscaUsuarioPeloToken(token);
			usuariodoid = usuRes.buscaUsuarioPeloId(usuarioid);
			
			TypedQuery<Justificativa> queryJustificativa = this.getEm().createQuery("select p.justificativa from Ponto p where p.usuario = :usuario and Month(p.data) = :mes and Year(p.data) = :ano order by p.data asc", Justificativa.class);
			
			if (usuario.getAcesso().getTipo().equals(AcessoTipo.NORMAL)) {
				queryJustificativa.setParameter("usuario", usuario);
			} else if (usuario.getAcesso().getTipo().equals(AcessoTipo.COORDENADOR)) {
				if (usuariodoid.getDepartamento().getId().equals(usuario.getDepartamento().getId())) {
					queryJustificativa.setParameter("usuario", usuariodoid);
				} else {
					throw erroUsuarioNaoPertenceAoCoordenadorLogado;
				}
			}
			
			queryJustificativa.setParameter("mes", mes);
			queryJustificativa.setParameter("ano", ano);
		
			List<Justificativa> lista = queryJustificativa.getResultList();
			
			return lista;
			
		} catch (NoResultException e) {
		}

		return null;
		
	}
	
}
