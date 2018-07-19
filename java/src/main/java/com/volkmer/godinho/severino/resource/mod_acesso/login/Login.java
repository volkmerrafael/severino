package com.volkmer.godinho.severino.resource.mod_acesso.login;

import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Login {
	
	private String nomeacesso;
	private String senha;
	private String usertoken;
	private String sessaotoken;
	private Usuario usuario;
	private String superuser;
	
}
