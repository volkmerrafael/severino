package com.volkmer.godinho.severino.resource.mod_acesso.login;

import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;

public class Login {
	
	private String nomeacesso;
	private String senha;
	private String usertoken;
	private String sessaotoken;
	private Usuario usuario;
	
	public String getNomeacesso() {
		return nomeacesso;
	}
	public void setNomeacesso(String acesso) {
		this.nomeacesso = acesso;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getUsertoken() {
		return usertoken;
	}
	public void setUsertoken(String usertoken) {
		this.usertoken = usertoken;
	}
	public String getSessaotoken() {
		return sessaotoken;
	}
	public void setSessaotoken(String sessaotoken) {
		this.sessaotoken = sessaotoken;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
