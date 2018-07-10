package com.volkmer.godinho.severino.resource.mod_geral.questao;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.mod_geral.Questao;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Questão")
@Path("/questao")
public class QuestaoController extends ControllerCRUD<Questao, QuestaoResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;

	@Override
	public QuestaoResource newResource() {
		return new QuestaoResource();
	}
	
	@GET
	@Path("/listar")
	@ApiOperation(value = "Listar Questões por Usuário")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Questao> listar() throws Exception {
		try (QuestaoResource qres = new QuestaoResource()) {
			return qres.listarQuestoesUsuario(userToken);	
		} catch (Exception e) {
			throw e;
		}
	}
}
