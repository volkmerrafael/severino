package com.volkmer.godinho.severino.resource.departamento;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.Departamento;

import io.swagger.annotations.Api;

@Api("Departamento")
@Path("/departamento")
public class DepartamentoController extends ControllerCRUD<Departamento, DepartamentoResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@Override
	public DepartamentoResource newResource() {
		return new DepartamentoResource();
	}
	
}
