package com.volkmer.godinho.severino.resource.importador.utils;

import java.lang.reflect.Field;

public class UtilsImportacao {

	
	public static String primeiraLetraMaiuscula(String texto){
		String temp = texto.substring(0,1).toUpperCase();
		if (temp.equals("_")){
			temp = texto.substring(1,2).toUpperCase();
			temp = "_" + temp + texto.substring(2); 
		}else{
			temp += texto.substring(1);
		}		
		return temp;
	}
	
	/**
	 * Copia os atributos contidos no objDestino, do objOrigem para o ObjDestino.
	 * @param objOrigem
	 * @param objDestino
	 */
	public static void copiaPropriedadesGeneric(Object objOrigem, Object objDestino){
		final Field[] fieldsOri = objOrigem.getClass().getDeclaredFields();
		final Field[] fieldsDes = objDestino.getClass().getDeclaredFields();
		Boolean contem;
		for (final Field fieldOri : fieldsOri) {
			if ( !fieldOri.getName().toUpperCase().equals( ("serialVersionUID" ).toUpperCase() ) ) {
				contem = false;
				for ( final Field fieldDes : fieldsDes ) {
					if ( fieldOri.getName().equals( fieldDes.getName() ) ) {
						contem = true;
						break;
					}
				}
				if ( contem ) {
					Object valor = Reflection.getValor(primeiraLetraMaiuscula(fieldOri.getName()), objOrigem);
					Reflection.setValor(primeiraLetraMaiuscula(fieldOri.getName()), objDestino,valor, fieldOri.getType());
				}
			}
		}
	}
	
	public static String removePontoZero(String value) {
		int indexOf = value.indexOf(".0");
		if (indexOf>=0) {
			value = value.substring(0, indexOf);
		}
		return value;
	}
	
}
