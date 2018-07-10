package com.volkmer.godinho.severino.resource.mod_jira.prioridade;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Prioridade Jira")
@Path("/jira/prioridade")
public class PrioridadeController {
	
	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;
	
	//Lista Prioridades das issues
	@GET
	@Path("/listar")
	@ApiOperation(value = "Listar Prioridas das Issues por Usuário")
	@Produces(MediaType.APPLICATION_JSON)
	public PrioridadesInfo listar() throws Exception {
		try (PrioridadeResource pres = new PrioridadeResource()) {
			return pres.listarPrioridadeJira(userToken);	
		} catch (Exception e) {
			throw e;
		}
	}
	
}
