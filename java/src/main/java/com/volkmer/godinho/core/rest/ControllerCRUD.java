package com.volkmer.godinho.core.rest;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.core.rest.filters.RestException;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class ControllerCRUD<Model, R extends ResourceCRUD<Model>> {
	
	public abstract R newResource();
	
	@GET
	@Path("/")
	public List<Model> get() throws Exception {
		try (R res = this.newResource()) {
			return res.buscaTotos();
		} catch (Exception e) {
			throw new RestException(e);
		}
	}
	
	@GET
	@Path("/{id}")
	public Model get(@PathParam("id") Long id) throws Exception {
		try (R res = this.newResource()) {
			Model usuario = res.busca(id);
			return usuario;
		} catch (Exception e) {
			throw new RestException(e);
		}
	}
	
	@POST
	@Path("/")
	public Model post(Model model) throws Exception {
		try (R res = this.newResource()) {
			res.incluir(model);
			return model;
		} catch (Exception e) {
			throw new RestException(e);
		}
	}
	
	@PUT
	@Path("/")
	public Model put(Model model) throws Exception {
		try (R res = this.newResource()) {
			res.alterar(model);
			return model;
		} catch (Exception e) {
			throw new RestException(e);
		}
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) throws Exception {
		try (R res = this.newResource()) {
			res.remover(id);
			return Response.ok().build();
		} catch (Exception e) {
			throw new RestException(e);
		}
	}
	
	@GET
	@Path("/help")
	@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
	public String help() {
		return "<div><h1>Pagina Help</h1></div>";
	}
	
}
