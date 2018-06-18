package com.volkmer.godinho.severino.resource.usuario;

import java.time.LocalDateTime;

import javax.persistence.NoResultException;
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
			
			Usuario usuario = new Usuario();
			
			Acesso acesso = new Acesso();
			
			if (model.getAcesso().getToken()!=null) {
				
				usuario = this.buscaUsuarioPeloToken(model.getAcesso().getToken());
				
				if (model.getData_admissao()!=null) {
					usuario.setData_admissao(model.getData_admissao());
				}
				if (model.getEmail()!="") {
					usuario.setEmail(model.getEmail());
				}
				
				acesso = usuario.getAcesso();
				
				if (model.getAcesso().getNomeacesso()!="") {
					acesso.setNomeacesso(model.getAcesso().getNomeacesso());
				}
				if (model.getAcesso().getSenha()!="") {
					acesso.setSenha(new Crypto().criptografar(model.getAcesso().getSenha()));
					
					acesso.setToken(
							new Crypto().criptografar(acesso.getNomeacesso()+";"+acesso.getData().toString()) +
							new Crypto().criptografar(acesso.getSenha()+";"+acesso.getData().toString())
						);
					
				}
				
				model.setAcesso(acesso);
				
			} else {
				
				acesso = model.getAcesso();
				acesso.setTipo(AcessoTipo.NORMAL);
				acesso.setSenha(new Crypto().criptografar(acesso.getSenha()));
				acesso.setData(LocalDateTime.now());
				acesso.setToken(
					new Crypto().criptografar(acesso.getNomeacesso()+";"+acesso.getData().toString()) +
					new Crypto().criptografar(acesso.getSenha()+";"+acesso.getData().toString())
				);
			
			}
			
			new AcessoResource(this).alterar(acesso);
			
		}
		
	}
	
	public Usuario buscarPeloAcesso(Acesso acesso) {
		
		TypedQuery<Usuario> query = this.getEm().createQuery("select u from Usuario u where u.acesso = :acesso", this.getModelClass());
		query.setParameter("acesso", acesso);
		Usuario usu = query.getSingleResult();
		
		return usu;
		
	}
	
	public Usuario buscaUsuarioPeloToken(String token) {
		
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
	
	public Usuario buscaUsuarioPeloId(Long id) {
		
		//Consulta usuario para mostrar somente informações do mesmo
		TypedQuery<Usuario> queryUsuario = this.getEm().createQuery("select u from Usuario u where u.id = :id", Usuario.class);
		queryUsuario.setParameter("id", id);
		Usuario usuario = queryUsuario.getSingleResult();
	
		return usuario;
		
	}
	
	public Usuario buscaUsuarioPorPis(Long pis) throws Exception {
		
		//Busca usuário pelo P.I.S.
		TypedQuery<Usuario> queryUsuario = this.getEm().createQuery("select u from Usuario u where u.pis = :pis", Usuario.class);
		queryUsuario.setParameter("pis", pis);		
		
		Usuario usuario = new Usuario();
		
		try {
			usuario = queryUsuario.getSingleResult();
		} catch (NoResultException e) {
			usuario = null;
		}
		
		return usuario;
		
	}
	
	public boolean ehUsarioAdmin(String userToken) {
		
		TypedQuery<Acesso> queryAcesso = this.getEm().createQuery("select a from Acesso a where a.token = :token", Acesso.class);
		queryAcesso.setParameter("token", userToken);
		Acesso acesso = queryAcesso.getSingleResult();
		
		if (acesso.getTipo().equals(AcessoTipo.ADMIN)) {
			return true;
		}		
		
		return false;
		
	}

}