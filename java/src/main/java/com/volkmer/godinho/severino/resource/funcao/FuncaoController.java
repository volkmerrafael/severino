package com.volkmer.godinho.severino.resource.funcao;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.Funcao;

import io.swagger.annotations.Api;

@Api("Função")
@Path("/funcao")
public class FuncaoController extends ControllerCRUD<Funcao, FuncaoResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;

	@HeaderParam("user-agent")
	String userAgent;
	
	@Override
	public FuncaoResource newResource() {
		return new FuncaoResource();
	}
	
}
