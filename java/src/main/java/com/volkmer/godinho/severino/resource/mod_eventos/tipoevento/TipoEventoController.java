package com.volkmer.godinho.severino.resource.mod_eventos.tipoevento;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.mod_eventos.TipoEvento;

import io.swagger.annotations.Api;

@Api("Tipo do Evento")
@Path("/tipoevento")
public class TipoEventoController extends ControllerCRUD<TipoEvento, TipoEventoResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;

	@Override
	public TipoEventoResource newResource() {
		return new TipoEventoResource();
	}
	
}
