package integration.usuario;

import javax.xml.ws.WebServiceException;

import org.junit.Assert;
import org.junit.Test;

import com.volkmer.godinho.core.rest.filters.RestException;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;

import integration.TesteCrud;

public class UsuarioTest extends TesteCrud<Usuario> {
	
	@Override
	protected String getUrlName() {
		return "usuario";
	}
	
	@Override
	protected Class<Usuario> getModelClass() {
		return Usuario.class;
	}

	@Override
	protected Long getId(Usuario obj) {
		return obj.getId();
	}
	
	@Override
	protected void assertCompara(Usuario obj1, Usuario obj2) {
		Assert.assertEquals(obj1.getNome(), obj2.getNome());
		Assert.assertEquals(obj1.getEmail(), obj2.getEmail());
	}

	@Override
	protected Usuario getObjGravar() {
		Usuario usuSalvar = new Usuario();
		usuSalvar.setNome("teste");
		usuSalvar.setEmail("teste@teste.com");
		return usuSalvar;
	}

	@Override
	protected void alterarObjeto(Usuario obj) {
		obj.setNome("teste2");
	}

}
