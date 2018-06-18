package com.volkmer.godinho.core.util;

public class ValidaData {
	
	public static Boolean validaDataFormatada( String date ) {
		
		try {
			
			String[] arrDate = date.split( "-" );
			
			if ( arrDate.length!=3 ) {
				return false;
			}
			
			Integer ano = Integer.parseInt( arrDate[0] );
			Integer mes = Integer.parseInt( arrDate[1] );
			Integer dia = Integer.parseInt( arrDate[2] );
			
			if ( ano<1800 || ano>3000 ) {
				return false;
			}
			
			if ( mes>12 || mes<1 ) {
				return false;
			}
			
			if ( dia<1 || dia>31 ) {
				return false;
			}
			
			return true;
			
		} catch ( Exception e ) {
			return false;
		}
		
	}
	
}

