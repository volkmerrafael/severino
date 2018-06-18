package com.volkmer.godinho.severino.resource.justificativa;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.Justificativa;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Justificativa")
@Path("/justificativa")
public class JustificativaController extends ControllerCRUD<Justificativa, JustificativaResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;

	@Override
	public JustificativaResource newResource() {
		return new JustificativaResource();
	}
	
	@GET
	@Path("/listar/{usuario}/{ano}/{mes}")
	@ApiOperation(value = "Listar Justificativa por Usuário Ano e Mês")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Justificativa> listaJustificativas(@PathParam("usuario") Long usuarioid, @PathParam("ano") Integer ano, @PathParam("mes") Integer mes) throws Exception {
		try (JustificativaResource pres = new JustificativaResource()) {
			return pres.listarJustificativa(usuarioid, userToken, ano, mes);
		} catch (Exception e) {
			throw e;
		}
	}
	
}
