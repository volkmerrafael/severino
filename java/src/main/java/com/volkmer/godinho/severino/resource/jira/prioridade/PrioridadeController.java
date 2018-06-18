package com.volkmer.godinho.severino.resource.jira.prioridade;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Prioridade Jira")
@Path("/jira/prioridade")
public class PrioridadeController {
	
	//Lista Prioridades das issues
	@GET
	@Path("/listar")
	@ApiOperation(value = "Listar Prioridas das Issues")
	@Produces(MediaType.APPLICATION_JSON)
	public PrioridadesInfo listar() throws Exception {
		try (PrioridadeResource pres = new PrioridadeResource()) {
			return pres.listarPrioridadeJira();	
		} catch (Exception e) {
			throw e;
		}
	}
	
}
