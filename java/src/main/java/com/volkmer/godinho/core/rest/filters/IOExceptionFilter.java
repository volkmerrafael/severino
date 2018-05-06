package com.volkmer.godinho.core.rest.filters;

import java.io.IOException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IOExceptionFilter implements ExceptionMapper<IOException> {

	@Override
	public Response toResponse(IOException e) {
		return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
	}
	
}