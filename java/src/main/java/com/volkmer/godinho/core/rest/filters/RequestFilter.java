package com.volkmer.godinho.core.rest.filters;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import com.volkmer.godinho.core.rest.RestSessao;
import com.volkmer.godinho.severino.entity.mod_acesso.Acesso;
import com.volkmer.godinho.severino.entity.mod_acesso.Sessao;
import com.volkmer.godinho.severino.resource.mod_acesso.acesso.AcessoResource;
import com.volkmer.godinho.severino.resource.mod_acesso.logacesso.ProcessaLogTransacao;
import com.volkmer.godinho.severino.resource.mod_acesso.sessao.SessaoResource;

@Provider
public class RequestFilter implements ContainerRequestFilter {

	@Inject
	RestSessao sessao;
	
	@Context
	HttpServletRequest context;
	
	private boolean verificaPermissaoTotal(String pathInfo) {
		return 
				(pathInfo.equals("/login") || 
				 pathInfo.indexOf("/teste")==0 || 
				 pathInfo.indexOf("/jira")==0 || 
				 pathInfo.indexOf("/configuracao")==0 ||
				 pathInfo.indexOf("/recuperacaodesenha")==0 ||
				 pathInfo.endsWith("swagger.json") ||  
				 pathInfo.lastIndexOf("/help")>=0);
	}
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		String pathInfo = context.getPathInfo();
		String usuariotoken = context.getHeader("User-Token");
		
		String tipo = requestContext.getRequest().getMethod();
		
		String data = null;
		 
		if (tipo.equals("PUT") || tipo.equals("POST")) {
			//StringBuilder buffer = new StringBuilder();
		    //BufferedReader reader = context.getReader();
		    //String line;
		    //while ((line = reader.readLine()) != null) {
		    //   buffer.append(line);
		    //}
		    //data = buffer.toString();
		}
		
		String ipAddress = context.getHeader("x-forwarded-for");
		if (ipAddress == null) {
		    ipAddress = context.getHeader("X_FORWARDED_FOR");
		    if (ipAddress == null){
		        ipAddress = context.getRemoteAddr();
		    }
		}
		
		//Insere log De Acesso
		try {
			new ProcessaLogTransacao().processar(usuariotoken, tipo, pathInfo, ipAddress, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (!this.verificaPermissaoTotal(pathInfo)) {
			

			String sessaotoken = context.getHeader("Session-Token");
			//String useragent = context.getHeader("User-Agent");
			//String ip = context.getRemoteHost()+"-"+context.getRemoteAddr();
			
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
