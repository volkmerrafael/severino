package com.volkmer.godinho.severino.resource.usuario;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.Usuario;

import io.swagger.annotations.Api;

@Api("Usuário")
@Path("/usuario")
public class UsuarioController extends ControllerCRUD<Usuario, UsuarioResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;

	@HeaderParam("user-agent")
	String userAgent;
	
	@Override
	public UsuarioResource newResource() {
		return new UsuarioResource();
	}
	
}
