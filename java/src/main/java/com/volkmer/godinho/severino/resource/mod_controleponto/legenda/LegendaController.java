package com.volkmer.godinho.severino.resource.mod_controleponto.legenda;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.mod_controleponto.Legenda;

import io.swagger.annotations.Api;

@Api("Legenda")
@Path("/legenda")
public class LegendaController extends ControllerCRUD<Legenda, LegendaResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;

	@Override
	public LegendaResource newResource() {
		return new LegendaResource();
	}
	
	//Lista o ponto de um período especifíco
	@GET
	@Path("/listar/{usuario}/{ano}/{mes}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Legenda> listar(@PathParam("usuario") Long usuarioid, @PathParam("ano") Integer ano, @PathParam("mes") Integer mes) throws Exception {
		try (LegendaResource pres = new LegendaResource()) {
			return pres.listarLegenda(usuarioid,userToken,ano,mes);	
		} catch (Exception e) {
			throw e;
		}
	}
	
}
