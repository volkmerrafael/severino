package com.volkmer.godinho.severino.resource.usuario;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.Usuario;

@Path("/usuario")
public class UsuarioController extends ControllerCRUD<Usuario, UsuarioResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@Override
	public UsuarioResource newResource() {
		return new UsuarioResource();
	}
	
}
