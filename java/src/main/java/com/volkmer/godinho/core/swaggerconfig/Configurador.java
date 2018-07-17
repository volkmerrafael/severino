package com.volkmer.godinho.core.swaggerconfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Swagger;
import io.swagger.models.auth.OAuth2Definition;


@ApplicationPath("api")
public class Configurador extends Application {

	public Configurador() {
		BeanConfig b = new BeanConfig();
		b.setVersion("1.0.0");
		b.setSchemes(new String[] {"http"});
		b.setHost("192.168.0.21:8082");
		b.setBasePath("severino/rest");
		//b.setResourcePackage(EchoRest.class.getPackage().getName());
		b.setTitle("severino");
		b.setDescription("Documentação Severino");
		b.setResourcePackage("com.volkmer.godinho.severino.resource");
		b.setScan(true);

		//Faz aparecer botão de Autorization no swagger, aqui é informado o método de login
		Swagger swagger = new Swagger();
		
		OAuth2Definition oauth2 = new OAuth2Definition();
        oauth2.password("http://" + swagger.getHost() + swagger.getBasePath()+"/login");
        swagger.securityDefinition("petstore_auth", oauth2);

	}
	
}
