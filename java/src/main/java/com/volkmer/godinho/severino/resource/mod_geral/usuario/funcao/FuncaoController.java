package com.volkmer.godinho.severino.resource.mod_geral.usuario.funcao;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Funcao;

import io.swagger.annotations.Api;

@Api("Função")
@Path("/funcao")
public class FuncaoController extends ControllerCRUD<Funcao, FuncaoResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;
	
	@Override
	public FuncaoResource newResource() {
		return new FuncaoResource();
	}
	
}
