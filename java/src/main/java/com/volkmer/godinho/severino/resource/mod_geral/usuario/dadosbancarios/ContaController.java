package com.volkmer.godinho.severino.resource.mod_geral.usuario.dadosbancarios;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.dadosbancarios.Conta;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Conta")
@Path("/conta")
public class ContaController extends ControllerCRUD<Conta, ContaResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;

	@Override
	public ContaResource newResource() {
		return new ContaResource();
	}
	
	@GET
	@Path("/listar")
	@ApiOperation(value = "Listar Conta do Usuário Logado")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Conta> listar() throws Exception {
		try (ContaResource conRes = new ContaResource()) {
			return conRes.listarContasDoUsuario(userToken);	
		} catch (Exception e) {
			throw e;
		}
	}
	
}
