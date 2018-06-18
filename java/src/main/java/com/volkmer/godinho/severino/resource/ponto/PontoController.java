package com.volkmer.godinho.severino.resource.ponto;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.Ponto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Ponto")
@Path("/ponto")
public class PontoController extends ControllerCRUD<Ponto, PontoResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;

	@Override
	public PontoResource newResource() {
		return new PontoResource();
	}
	
	@GET
	@Path("/listar/{usuario}/{ano}/{mes}")
	@ApiOperation(value = "Listar Pontos por Usuário - Ano - Mês")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ponto> listar(@PathParam("usuario") Long usuarioid, @PathParam("ano") Integer ano, @PathParam("mes") Integer mes) throws Exception {
		try (PontoResource pres = new PontoResource()) {
			return pres.listarPontos(usuarioid,userToken,ano,mes,null);	
		} catch (Exception e) {
			throw e;
		}
	}

	@GET
	@Path("/listar/{usuario}/{ano}/{mes}/{status}")
	@ApiOperation(value = "Listar Pontos por Usuário - Ano - Mês - Status")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ponto> listarPosStatus(@PathParam("usuario") Long usuarioid, @PathParam("ano") Integer ano, @PathParam("mes") Integer mes, @PathParam("status") PontoStatus status) throws Exception {
		try (PontoResource pres = new PontoResource()) {
			return pres.listarPontos(usuarioid,userToken,ano,mes,status);	
		} catch (Exception e) {
			throw e;
		}
	}
	
}
