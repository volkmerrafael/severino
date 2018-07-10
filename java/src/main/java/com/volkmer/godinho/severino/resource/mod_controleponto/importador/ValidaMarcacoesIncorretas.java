package com.volkmer.godinho.severino.resource.mod_controleponto.importador;

public class ValidaMarcacoesIncorretas {

	public Boolean ehCorreta(
			String e1, String s1, String e2, String s2, 
			String e3, String s3, String e4, String s4) {
		
		Integer contador = 0;
		
		if (e1!=null && !e1.equals("")) {
			contador++;
		}
		if (s1!=null && !s1.equals("")) {
			contador++;
		}
		if (e2!=null && !e2.equals("")) {
			contador++;
		}
		if (s2!=null && !s2.equals("")) {
			contador++;
		}
		if (e3!=null && !e3.equals("")) {
			contador++;
		}
		if (s3!=null && !s3.equals("")) {
			contador++;
		}
		if (e4!=null && !e4.equals("")) {
			contador++;
		}
		if (s4!=null && !s4.equals("")) {
			contador++;
		}
		
		return this.ePar(contador);
		
	}	
	
	public boolean ePar(int numero)
	{
		if(numero % 2 == 0)
		   return true;
		else
		   return false;
	}
	
}
