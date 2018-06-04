package com.volkmer.godinho.severino.resource.controlehoras;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.volkmer.godinho.severino.entity.ControleHoras;

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
	@Path("/listar/{ano}/{mes}")
	@Produces(MediaType.APPLICATION_JSON)
	public ControleHoras listar(@PathParam("ano") Integer ano, @PathParam("mes") Integer mes) throws Exception {
		try (ControleHorasResource pres = new ControleHorasResource()) {
			return pres.listarControleHoras(userToken,ano,mes);	
		} catch (Exception e) {
			throw e;
		}
	}
	
}
