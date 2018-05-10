package com.volkmer.godinho.core.rest.filters;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import com.volkmer.godinho.core.rest.RestSessao;
import com.volkmer.godinho.severino.entity.Acesso;
import com.volkmer.godinho.severino.entity.Sessao;
import com.volkmer.godinho.severino.resource.acesso.AcessoResource;
import com.volkmer.godinho.severino.resource.sessao.SessaoResource;

@Provider
public class RequestFilter implements ContainerRequestFilter {

	@Inject
	RestSessao sessao;
	
	@Context
	HttpServletRequest context;
	
	private boolean verificaPermissaoTotal(String pathInfo) {
		return (pathInfo.equals("/login") || pathInfo.indexOf("/teste")==0 || pathInfo.lastIndexOf("/help")>=0);
	}
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		String pathInfo = context.getPathInfo();
		if (!this.verificaPermissaoTotal(pathInfo)) {
			
			String usuariotoken = context.getHeader("User-Token");
			String sessaotoken = context.getHeader("Session-Token");
			String useragent = context.getHeader("User-Agent");
			String ip = context.getRemoteHost()+"-"+context.getRemoteAddr();
			
			if (usuariotoken==null || sessaotoken==null) {
				throw new IOException("Permissão negada");
			}
			
			try (
				SessaoResource sesRes = new SessaoResource();
				AcessoResource aceRes = new AcessoResource();
			) {
				
				Sessao sessao = sesRes.buscaPelaToken(sessaotoken);
				Acesso acesso = aceRes.buscaPorToken(usuariotoken);
				
				if (sessao==null) {
					throw new IOException("Sessão expirada");
				}
				if (acesso==null) {
					throw new IOException("Sessão expirada");
				}
				
				if (!acesso.getId().equals(sessao.getAcesso().getId())) {
					throw new IOException("Sessão expirada");
				}
				
				/*if (!sessao.getUseragent().equals(useragent) || !sessao.getIp().equals(ip)) {
					throw new IOException("Sessão expirada");
				}*/
				
				
			} catch (Exception e) {
				throw new IOException(e);
			}
			
			sessao.setUsuariotoken(usuariotoken);
			sessao.setSessiontoken(sessaotoken);
			
		}
		
	}

}
