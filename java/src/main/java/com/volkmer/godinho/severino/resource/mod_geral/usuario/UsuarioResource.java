package com.volkmer.godinho.severino.resource.mod_geral.usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.crypto.Crypto;
import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.core.util.AcaoTipo;
import com.volkmer.godinho.severino.entity.mod_acesso.Acesso;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.resource.mod_acesso.acesso.AcessoResource;
import com.volkmer.godinho.severino.resource.mod_acesso.acesso.AcessoTipo;
import com.volkmer.godinho.severino.resource.mod_geral.usuario.validacao.ValidaCamposObrigatorios;
import com.volkmer.godinho.severino.resource.mod_geral.usuario.validacao.ValidaDuplicacaoDeRegistros;
import com.volkmer.godinho.severino.resource.mod_geral.usuario.validacao.ValidaSeExisteUsuarioParaOToken;

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
		
	}
	
	@SuppressWarnings("resource")
	@Override
	protected void incluirPre(Usuario model) throws Exception {
		
		new ValidaCamposObrigatorios().validar(model,AcaoTipo.INCLUSAO);
		new ValidaDuplicacaoDeRegistros().validar(model);
		
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
		
		new ValidaCamposObrigatorios().validar(model,AcaoTipo.ALTERACAO);
		new ValidaDuplicacaoDeRegistros().validar(model);
		
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
		
		//Verifica se usuário tem acesso de admin pelo token
		TypedQuery<Acesso> queryAcesso = this.getEm().createQuery("select a from Acesso a where a.token = :token", Acesso.class);
		queryAcesso.setParameter("token", userToken);
		Acesso acesso = queryAcesso.getSingleResult();
		
		if (acesso.getTipo().equals(AcessoTipo.ADMIN)) {
			return true;
		}		
		
		return false;
		
	}
	
	public boolean ehUsarioCoordenador(String userToken) {
		
		//Verifica se usuário tem acesso de coordenador pelo token
		TypedQuery<Acesso> queryAcesso = this.getEm().createQuery("select a from Acesso a where a.token = :token", Acesso.class);
		queryAcesso.setParameter("token", userToken);
		Acesso acesso = queryAcesso.getSingleResult();
		
		if (acesso.getTipo().equals(AcessoTipo.COORDENADOR)) {
			return true;
		}		
		
		return false;
		
	}
	
	public List<Usuario> listarUsuariosDoCoordenador(String userToken) throws Exception {
		
		Usuario usuario = new ValidaSeExisteUsuarioParaOToken().validar(userToken);
		
		if (this.ehUsarioCoordenador(userToken)) {
			
			TypedQuery<Usuario> queryUsuario = this.getEm().createQuery("select p from Usuario p where p.departamento = :departamento and p.id <> :id", Usuario.class);
			queryUsuario.setParameter("departamento", usuario.getDepartamento());
			queryUsuario.setParameter("id", usuario.getId());
			
			List<Usuario> lista = queryUsuario.getResultList();
			
			return lista;

		}
		
		return null;
		
	}
	
	public List<Usuario> listarCoordenadores(String userToken) throws Exception {
		
		List<Usuario> listaCoordenador = new ArrayList<Usuario>();
		
		Usuario usuario = new ValidaSeExisteUsuarioParaOToken().validar(userToken);
		
		if (this.ehUsarioAdmin(usuario.getAcesso().getToken())) {
			
			TypedQuery<Usuario> queryUsuario = this.getEm().createQuery("select p from Usuario p", Usuario.class);			
			List<Usuario> lista = queryUsuario.getResultList();
			
			for (Usuario usu : lista) {
				if (usu.getAcesso().getTipo().equals(AcessoTipo.COORDENADOR)) {
					listaCoordenador.add(usu);
				}
			}
			
			return listaCoordenador;

		}
		
		return null;
		
	}
	
	public List<Usuario> listarUsuariosAcessoNormal() throws Exception {
		
		List<Usuario> listaFinal = new ArrayList<Usuario>();
		
		TypedQuery<Usuario> queryUsuario = this.getEm().createQuery("select p from Usuario p", Usuario.class);			
		List<Usuario> lista = queryUsuario.getResultList();
		
		for (Usuario usu : lista) {
			if (usu.getAcesso().getTipo().equals(AcessoTipo.NORMAL)) {
				listaFinal.add(usu);
			}
		}
		
		return listaFinal;
		
	}

}