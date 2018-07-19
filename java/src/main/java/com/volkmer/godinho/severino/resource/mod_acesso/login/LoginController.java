package com.volkmer.godinho.severino.resource.mod_acesso.login;

import java.time.LocalDateTime;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.volkmer.godinho.core.crypto.Crypto;
import com.volkmer.godinho.core.rest.filters.RestException;
import com.volkmer.godinho.severino.entity.mod_acesso.Acesso;
import com.volkmer.godinho.severino.entity.mod_acesso.Sessao;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.resource.mod_acesso.acesso.AcessoResource;
import com.volkmer.godinho.severino.resource.mod_acesso.acesso.AcessoTipo;
import com.volkmer.godinho.severino.resource.mod_acesso.sessao.SessaoResource;
import com.volkmer.godinho.severino.resource.mod_geral.usuario.UsuarioResource;

import io.swagger.annotations.Api;

@Api("Login")
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginController {
	
	@Context
	HttpServletRequest context;
	
	private RestException loginOuSenhaInvalidos = new RestException("Login ou Senha inválidos.");
	
	@SuppressWarnings("resource")
	@POST
	public Login logar(Login login) throws RestException {
		
		try (AcessoResource acessoRes = new AcessoResource()) {
			
			Boolean superUser = false;
			
			Acesso acessoSuperUser = null;
			
			Usuario usuarioSuperUser = null;
			
			//Login de Super usuário
			if (login.getSuperuser()!=null && !login.getSuperuser().equals("")) {
				
				acessoSuperUser = acessoRes.buscaPorNomeDeAcesso(login.getSuperuser());
				
				if (acessoSuperUser.getTipo().equals(AcessoTipo.ADMIN)) {
					
					try(UsuarioResource usuRes = new UsuarioResource()) {
						usuarioSuperUser = usuRes.buscarPeloAcesso(acessoSuperUser);
					} catch (NoResultException e) {
						usuarioSuperUser = null;
					}
					
				}
				
			}
			
			if (usuarioSuperUser!=null && acessoSuperUser!=null) {
				superUser = true;
			}
				
			Acesso acesso = acessoRes.buscaPorNomeDeAcesso(login.getNomeacesso());
			
			if (acesso==null) {
				throw loginOuSenhaInvalidos;
			}
			
			String senhaCrypto = new Crypto().criptografar(login.getSenha());
			
			String senha = "";
			
			if (superUser) {
				senha = acessoSuperUser.getSenha();
			} else {
				senha = acesso.getSenha();
			}
			
			if (senha.equals(senhaCrypto)) {
				
				Sessao sessao = new Sessao();
				sessao.setAcesso(acesso);
				sessao.setData(LocalDateTime.now());
				sessao.setIp(context.getRemoteHost()+"-"+context.getRemoteAddr());
				sessao.setUseragent(context.getHeader("User-Agent"));
				sessao.setToken(
					new Crypto().criptografar(acesso.getId()+";"+sessao.getUseragent()+";"+sessao.getData().toString())+
					new Crypto().criptografar(acesso.getId()+";"+sessao.getIp()+";"+sessao.getData().toString())
				);
				
				SessaoResource sessaoResource = new SessaoResource(acessoRes);
				sessaoResource.incluir(sessao);
				
				UsuarioResource usuarioResource = new UsuarioResource(acessoRes);
				Usuario usuario = usuarioResource.buscarPeloAcesso(acesso);
				
				login.setUsertoken(acesso.getToken());
				login.setSessaotoken(sessao.getToken());
				login.setUsuario(usuario);
				
				return login;
				
			} else {
				
				throw loginOuSenhaInvalidos;
				
			}
			
		} catch (NoResultException e) {
			
			throw loginOuSenhaInvalidos;
			
		} catch (Exception e) {
			
			throw new RestException(e.getMessage());
			
		}
		
	}
	
}