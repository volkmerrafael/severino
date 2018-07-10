package com.volkmer.godinho.core.rest.filters;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RestExceptionFilter implements ExceptionMapper<RestException> {

	@Override
	public Response toResponse(RestException e) {
		if (e.getDetail()!=null && !e.getDetail().equals("")) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()+" - "+e.getDetail()).build();
		} else {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}	
	}
	
}