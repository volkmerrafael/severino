package com.volkmer.godinho.core.rest.filters;

public class RestException extends Exception {

	private static final long serialVersionUID = 1L;
	private StringBuilder detail;
	
	public RestException() {
	}
	
	public RestException(Exception e) {
		super(e.getMessage());
	}
	
	public RestException(String erro) {
		super(erro);
	}
	
	public RestException addDetalhe( String texto ) {
		if ( this.detail==null ) {
			this.detail = new StringBuilder();
		}
		if ( this.detail.length()>0 ) {
			this.detail.append( "\n" );
		}
		this.detail.append( texto );
		return this;
	}

	public String getDetail() {
		if ( detail!=null ) {
			return detail.toString();
		} else {
			return null;
		}
	}
	
	public String toResponseXml() {
		
		StringBuilder xml = new StringBuilder()
		.append( "<ExceptionResponse>" )
			.append( "<message>" ).append( this.getMessage() ).append( "</message>" )
			.append( "<detail>" ).append( this.getDetail() ).append( "</detail>" )
		.append( "</ExceptionResponse>" );
		
		return xml.toString();
		
	}
}

