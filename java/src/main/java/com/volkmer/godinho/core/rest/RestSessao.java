package com.volkmer.godinho.core.rest;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RestSessao {

	private String usuariotoken;
	private String sessiontoken;
	
	public String getUsuariotoken() {
		return usuariotoken;
	}
	public void setUsuariotoken(String usuariotoken) {
		this.usuariotoken = usuariotoken;
	}
	public String getSessiontoken() {
		return sessiontoken;
	}
	public void setSessiontoken(String sessiontoken) {
		this.sessiontoken = sessiontoken;
	}
	
}
