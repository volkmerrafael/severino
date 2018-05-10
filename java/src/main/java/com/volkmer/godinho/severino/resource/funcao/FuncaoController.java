package com.volkmer.godinho.severino.resource.funcao;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.Funcao;

@Path("/funcao")
public class FuncaoController extends ControllerCRUD<Funcao, FuncaoResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@Override
	public FuncaoResource newResource() {
		return new FuncaoResource();
	}
	
}
