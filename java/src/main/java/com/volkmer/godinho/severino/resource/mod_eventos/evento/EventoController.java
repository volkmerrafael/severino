package com.volkmer.godinho.severino.resource.mod_eventos.evento;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.mod_eventos.Evento;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Evento")
@Path("/evento")
public class EventoController extends ControllerCRUD<Evento, EventoResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;

	@Override
	public EventoResource newResource() {
		return new EventoResource();
	}
	
	@GET
	@Path("/participar/{id}")
	@ApiOperation(value = "Participar do Evento")
	@Produces(MediaType.APPLICATION_JSON)
	public void participar(@PathParam("id") Long idevento ) throws Exception {
		try (EventoResource eveRes = new EventoResource()) {
			eveRes.participar(userToken,idevento);	
		} catch (Exception e) {
			throw e;
		}
	}
	
}
