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
import io.swagger.annotations.ApiOperation;

@Api("Ponto")
@Path("/ponto")
public class PontoController {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;

	@GET
	@Path("/listar/periodos")
	@ApiOperation(value = "Listar Períodos que Usuário tem Ponto")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AnoMes> listaPeriodoComInfo() throws Exception {
		try (PontoResource pres = new PontoResource()) {
			return pres.listarPeriodos(userToken);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@GET
	@Path("/listar/{ano}/{mes}")
	@ApiOperation(value = "Listar Pontos por Ano e Mês")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ponto> listar(@PathParam("ano") Integer ano, @PathParam("mes") Integer mes) throws Exception {
		try (PontoResource pres = new PontoResource()) {
			return pres.listarPontos(userToken,ano,mes,null);	
		} catch (Exception e) {
			throw e;
		}
	}

	@GET
	@Path("/listar/{ano}/{mes}/{status}")
	@ApiOperation(value = "Listar Pontos por Ano e Mês")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ponto> listarPosStatus(@PathParam("ano") Integer ano, @PathParam("mes") Integer mes, @PathParam("status") PontoStatus status) throws Exception {
		try (PontoResource pres = new PontoResource()) {
			return pres.listarPontos(userToken,ano,mes,status);	
		} catch (Exception e) {
			throw e;
		}
	}
}
