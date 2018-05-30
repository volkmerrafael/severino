package com.volkmer.godinho.severino.resource.jornada;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.Jornada;

import io.swagger.annotations.Api;

@Api("Jornada")
@Path("/jornada")
public class JornadaController extends ControllerCRUD<Jornada, JornadaResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;

	@Override
	public JornadaResource newResource() {
		return new JornadaResource();
	}
	
	//Lista o ponto de um período especifíco
	@GET
	@Path("/listar/{ano}/{mes}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Jornada> listar(@PathParam("ano") Integer ano, @PathParam("mes") Integer mes) throws Exception {
		try (JornadaResource pres = new JornadaResource()) {
			return pres.listarJornada(userToken,ano,mes);	
		} catch (Exception e) {
			throw e;
		}
	}
}
