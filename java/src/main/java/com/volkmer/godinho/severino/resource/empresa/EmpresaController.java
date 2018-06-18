package com.volkmer.godinho.severino.resource.empresa;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.Empresa;

import io.swagger.annotations.Api;

@Api("Empresa")
@Path("/empresa")
public class EmpresaController extends ControllerCRUD<Empresa, EmpresaResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;

	@Override
	public EmpresaResource newResource() {
		return new EmpresaResource();
	}
	
}
