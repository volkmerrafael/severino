package integration.teste;

import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.ClientBuilder;

import org.junit.Test;

import com.volkmer.godinho.severino.entity.ObjetoTeste;

public class TesteRestClientTest {
	
	private final String url = "http://localhost:8082/severino/rest/teste/json";
	
	@Test
	public void getObjeto() {
		
		ObjetoTeste obj = ClientBuilder.newClient().target(url).request().get(ObjetoTeste.class);
		assertNotNull(obj);
		
	}
	
}
