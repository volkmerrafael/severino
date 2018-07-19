package com.volkmer.godinho.severino.resource.mod_geral.usuario.imagem;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.core.util.imagem.Redimensionar;
import com.volkmer.godinho.severino.entity.mod_geral.imagem.Imagem;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.resource.mod_geral.usuario.UsuarioResource;

public class ImagemResource extends ResourceCRUD<Imagem> {
	
	public ImagemResource() {
	}
	
	@Override
	public Class<Imagem> getModelClass() {
		return Imagem.class;
	}

	public Imagem buscaImagem(Long usuarioid) throws Exception {
			
		try (UsuarioResource usuRes = new UsuarioResource()) {
			Usuario usuario = usuRes.busca(usuarioid);
			
			//Busca Imagem
			TypedQuery<Imagem> queryImagem = this.getEm().createQuery("select u from Imagem u where u.usuario = :usuario", Imagem.class);
			queryImagem.setParameter("usuario", usuario);	
			
			return queryImagem.getSingleResult();
			
		} catch (NoResultException e) {
		}

		return null;
	}
	
	@Override
	protected void incluirPre(Imagem model) throws Exception {
		model.setFoto(new Redimensionar().fazer(model.getFoto(), 154, 192));
	}

	@Override
	protected void alterarPre(Imagem model) throws Exception {
		model.setFoto(new Redimensionar().fazer(model.getFoto(), 154, 192));
	}
	
}