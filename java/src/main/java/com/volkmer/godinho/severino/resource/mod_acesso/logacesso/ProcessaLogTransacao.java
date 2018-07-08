package com.volkmer.godinho.severino.resource.mod_acesso.logacesso;

import javax.persistence.NoResultException;

import com.volkmer.godinho.severino.entity.mod_acesso.LogAcesso;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.resource.mod_geral.usuario.UsuarioResource;

public class ProcessaLogTransacao {

	public void processar(String token, String tipo, String metodo, String ipAddress, String data) throws Exception {
	
		
		try (LogAcessoResource lres = new LogAcessoResource()) {
			
			LogAcesso logAcesso = new LogAcesso();
			logAcesso.setMetodo(metodo);
			logAcesso.setIp(ipAddress);
			logAcesso.setConteudo(data);
			
			if (tipo=="POST" && metodo.indexOf("login")!=-1) {
				logAcesso.setTipo(TipoLogAcesso.LOGIN);
			} else if (tipo=="PUT") {
				logAcesso.setTipo(TipoLogAcesso.ALERACAO);
			} else if (tipo=="POST") {
				logAcesso.setTipo(TipoLogAcesso.INCLUSAO);
			} else if (tipo=="DELETE") {
				logAcesso.setTipo(TipoLogAcesso.EXCLUSAO);
			} else if (tipo=="GET") {
				logAcesso.setTipo(TipoLogAcesso.CONSULTA);
			}
			
			Usuario usuario = new Usuario();
			
			try (UsuarioResource ures = new UsuarioResource()) {
				usuario = ures.buscaUsuarioPeloToken(token);
				logAcesso.setUsuario(usuario);
			} catch (NoResultException e) {				
			}
			
			lres.incluir(logAcesso);
			
		} catch (NoResultException e) {
			
		}

	}
	
}
