package com.volkmer.godinho.severino.resource.legenda;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.Legenda;

import io.swagger.annotations.Api;

@Api("Legenda")
@Path("/legenda")
public class LegendaController extends ControllerCRUD<Legenda, LegendaResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;

	@Override
	public LegendaResource newResource() {
		return new LegendaResource();
	}
	
}
