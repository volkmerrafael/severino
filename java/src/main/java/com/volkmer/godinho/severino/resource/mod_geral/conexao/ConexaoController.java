package com.volkmer.godinho.severino.resource.mod_geral.conexao;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.mod_geral.Conexao;

import io.swagger.annotations.Api;

@Api("Conexão")
@Path("/conexao")
public class ConexaoController extends ControllerCRUD<Conexao, ConexaoResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;

	@Override
	public ConexaoResource newResource() {
		return new ConexaoResource();
	}
	
}
