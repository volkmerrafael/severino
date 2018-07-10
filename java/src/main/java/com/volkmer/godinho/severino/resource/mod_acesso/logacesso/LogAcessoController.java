package com.volkmer.godinho.severino.resource.mod_acesso.logacesso;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.mod_acesso.LogAcesso;

import io.swagger.annotations.Api;

@Api("Log Acesso")
@Path("/logacesso")
public class LogAcessoController extends ControllerCRUD<LogAcesso, LogAcessoResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;

	@Override
	public LogAcessoResource newResource() {
		return new LogAcessoResource();
	}
	
}
