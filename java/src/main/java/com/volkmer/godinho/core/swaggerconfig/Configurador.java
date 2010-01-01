package com.volkmer.godinho.core.swaggerconfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;


@ApplicationPath("api")
public class Configurador extends Application {

	public Configurador() {
		BeanConfig b = new BeanConfig();
		b.setVersion("1.0.0");
		b.setSchemes(new String[] {"http"});
		b.setHost("localhost:8082");
		b.setBasePath("my-rest-1.0.0/api");
		//b.setResourcePackage(EchoRest.class.getPackage().getName());
		b.setTitle("severino");
		b.setDescription("severinokljl");
		b.setResourcePackage("com.volkmer.godinho");
		b.setScan(true);
	}
	
}
