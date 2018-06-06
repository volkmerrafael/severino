package com.volkmer.godinho.severino.resource.usuario;

import java.time.LocalDateTime;

import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.crypto.Crypto;
import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.Acesso;
import com.volkmer.godinho.severino.entity.Usuario;
import com.volkmer.godinho.severino.resource.acesso.AcessoResource;
import com.volkmer.godinho.severino.resource.acesso.AcessoTipo;

public class UsuarioResource extends ResourceCRUD<Usuario> {

	public UsuarioResource() {
	}
	
	public UsuarioResource(ResourceCRUD<?> res) {
		super(res);
	}
	
	@Override
	public Class<Usuario> getModelClass() {
		return Usuario.class;
	}
	
	@Override
	protected void buscaPos(Usuario model) throws Exception {
		
		try (AcessoResource pres = new AcessoResource()) {
			model.setAcesso(pres.busca(model.getId()));
		} catch (Exception e) {
			throw e;
		}
		//model.setAcesso(null);
		
	}
	
	@SuppressWarnings("resource")
	@Override
	protected void incluirPre(Usuario model) throws Exception {
		
		if (model.getAcesso()!=null) {
			Acesso acesso = model.getAcesso();
			if (acesso.getTipo()==null) {
				acesso.setTipo(AcessoTipo.NORMAL);
			}
			acesso.setSenha(new Crypto().criptografar(acesso.getSenha()));
			acesso.setData(LocalDateTime.now());
			acesso.setToken(
				new Crypto().criptografar(acesso.getNomeacesso()+";"+acesso.getData().toString()) +
				new Crypto().criptografar(acesso.getSenha()+";"+acesso.getData().toString())
			);
			new AcessoResource(this).incluir(acesso);
		}
		
	}
	
	@SuppressWarnings("resource")
	@Override
	protected void alterarPre(Usuario model) throws Exception {
		
		if (model.getAcesso()!=null) {
			Acesso acesso = model.getAcesso();
			acesso.setTipo(AcessoTipo.NORMAL);
			acesso.setSenha(new Crypto().criptografar(acesso.getSenha()));
			acesso.setData(LocalDateTime.now());
			acesso.setToken(
				new Crypto().criptografar(acesso.getNomeacesso()+";"+acesso.getData().toString()) +
				new Crypto().criptografar(acesso.getSenha()+";"+acesso.getData().toString())
			);
			new AcessoResource(this).alterar(acesso);
		}
		
	}
	
	public Usuario buscarPeloToken(String token) {
		
		@SuppressWarnings("resource")
		AcessoResource res = new AcessoResource(this);
		Acesso acesso = res.buscaPorToken(token);
		
		TypedQuery<Usuario> query = this.getEm().createQuery("select u from Usuario u where u.acesso = :acesso", this.getModelClass());
		query.setParameter("acesso", acesso);
		Usuario usu = query.getSingleResult();
		
		return usu;
		
	}
	
	public Usuario buscarPeloAcesso(Acesso acesso) {
		
		TypedQuery<Usuario> query = this.getEm().createQuery("select u from Usuario u where u.acesso = :acesso", this.getModelClass());
		query.setParameter("acesso", acesso);
		Usuario usu = query.getSingleResult();
		
		return usu;
		
	}

}