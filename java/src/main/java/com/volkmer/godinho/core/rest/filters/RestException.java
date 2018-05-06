package com.volkmer.godinho.core.rest.filters;

public class RestException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public RestException() {
	}
	
	public RestException(Exception e) {
		super(e.getMessage());
	}
	
	public RestException(String erro) {
		super(erro);
	}

}

