package integration;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

public abstract class TesteCrud<Model> {

	protected abstract String getUrlName();
	protected abstract Class<Model> getModelClass();
	protected abstract void assertCompara(Model obj1, Model obj2);
	protected abstract Long getId(Model obj);
	protected abstract Model getObjGravar();
	protected abstract void alterarObjeto(Model obj);
	
	private String getUrl() {
		return "http://localhost:8082/severino/rest/"+this.getUrlName();
	}
	
	@Test
	public void crudTest() {
		
		//gravar
		Model objGravar = this.getObjGravar();
		
		Model objGravado = ClientBuilder.newClient().target(this.getUrl()).request().post(Entity.entity(objGravar, MediaType.APPLICATION_JSON), this.getModelClass());
		Assert.assertNotNull(objGravado);
		this.assertCompara(objGravar, objGravado);

		Model usuSalvo2 = ClientBuilder.newClient().target(this.getUrl()).path("/{id}").resolveTemplate("id", this.getId(objGravado)).request().get(this.getModelClass());
		Assert.assertNotNull(usuSalvo2);
		Assert.assertEquals(this.getId(objGravado), this.getId(usuSalvo2));
		this.assertCompara(objGravar, usuSalvo2);

		//alterar
		this.alterarObjeto(objGravado);
		Model usuAlterado = ClientBuilder.newClient().target(this.getUrl()).request().put(Entity.entity(objGravado, MediaType.APPLICATION_JSON), this.getModelClass());
		Assert.assertNotNull(usuAlterado);
		Assert.assertEquals(this.getId(usuAlterado), this.getId(objGravado));
		this.assertCompara(usuAlterado, objGravado);
		
		//remover
		Response response = ClientBuilder.newClient().target(this.getUrl()).path("/{id}").resolveTemplate("id", this.getId(usuAlterado)).request().delete();
		Assert.assertEquals("Objeto remover.", Response.ok().build().getStatus(), response.getStatus());
		
		Model obj = ClientBuilder.newClient().target(this.getUrl()).path("/{id}").resolveTemplate("id", this.getId(usuAlterado)).request().get(this.getModelClass());
		Assert.assertNull(obj);
		
	}


	
}
