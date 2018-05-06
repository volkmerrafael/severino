package com.volkmer.godinho.severino.resource.ponto;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.volkmer.godinho.severino.entity.Ponto;

@Path("/ponto")
public class PontoController {

	@HeaderParam("user-token")
	String userToken;
	
	@SuppressWarnings("resource")

	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ponto> listar() throws Exception {
		return new PontoResource().listarPontos(userToken);		
		
	}
	
}
