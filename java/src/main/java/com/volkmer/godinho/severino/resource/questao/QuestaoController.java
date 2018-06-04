package com.volkmer.godinho.severino.resource.questao;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.Questao;

import io.swagger.annotations.Api;

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
	
}
