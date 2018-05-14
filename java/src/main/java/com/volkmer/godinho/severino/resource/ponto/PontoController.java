package com.volkmer.godinho.severino.resource.ponto;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.volkmer.godinho.severino.entity.AnoMes;
import com.volkmer.godinho.severino.entity.Ponto;

import io.swagger.annotations.Api;

@Api("Ponto")
@Path("/ponto")
public class PontoController {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;

	//Lista períodos em que o usuário tem o ponto importado
	@SuppressWarnings("resource")
	@GET
	@Path("/listar/periodos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AnoMes> listaPeriodoComInfo() throws Exception {
		return new PontoResource().listarPeriodos(userToken);
	}
	
	//Lista o ponto de um período especifíco
	@SuppressWarnings("resource")
	@GET
	@Path("/listar/{ano}/{mes}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ponto> listar(@PathParam("ano") Integer ano, @PathParam("mes") Integer mes) throws Exception {
		return new PontoResource().listarPontos(userToken,ano,mes);		
	}
	
}
