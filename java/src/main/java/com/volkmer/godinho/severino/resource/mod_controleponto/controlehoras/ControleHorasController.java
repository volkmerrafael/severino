package com.volkmer.godinho.severino.resource.mod_controleponto.controlehoras;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.volkmer.godinho.severino.entity.mod_controleponto.ControleHoras;

import io.swagger.annotations.Api;

@Api("Controle Horas")
@Path("/controlehoras")
public class ControleHorasController {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;
	
	//Lista o controle de horas
	@GET
	@Path("/listar/{usuario}/{ano}/{mes}")
	@Produces(MediaType.APPLICATION_JSON)
	public ControleHoras listar(@PathParam("usuario") Long usuarioid, @PathParam("ano") Integer ano, @PathParam("mes") Integer mes) throws Exception {
		try (ControleHorasResource pres = new ControleHorasResource()) {
			return pres.listarControleHoras(usuarioid,userToken,ano,mes);	
		} catch (Exception e) {
			throw e;
		}
	}
	
	//Lista o controle Horas Ano
	@GET
	@Path("/listar/{usuario}/{ano}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ControleHoras> listar(@PathParam("usuario") Long usuarioid, @PathParam("ano") Integer ano) throws Exception {
		try (ControleHorasResource pres = new ControleHorasResource()) {
			return pres.listarControleHorasAno(usuarioid,userToken,ano);	
		} catch (Exception e) {
			throw e;
		}
	}
	
}
