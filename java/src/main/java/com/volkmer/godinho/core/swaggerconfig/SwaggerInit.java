package com.volkmer.godinho.core.swaggerconfig;

import io.swagger.models.Swagger;

public interface SwaggerInit {
	public void onCreate(Swagger swagger) throws Exception;
}
