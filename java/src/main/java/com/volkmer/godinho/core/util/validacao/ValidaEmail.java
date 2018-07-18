package com.volkmer.godinho.core.util.validacao;

public class ValidaEmail {
	
	public Boolean validaEmail( String email ) {
		
		try {
			
				if (email.indexOf(" ")!=-1) {
					return false;
				}
				if ( email.indexOf("@")==-1) {
					return false;
				} else 
				if ( email.indexOf("@.")!=-1) {
					return false;
				} else {
					String temporaria = email.substring(email.indexOf("@"), email.length());
					if (temporaria.indexOf(".")==-1) {
						return false;	
					}
				}
				
				return email.matches( "[a-zA-Z0-9@._\\-\\s]{"+email.length()+"}" );
			
		} catch ( Exception e ) {
			return false;
		}
		
	}
	
}

