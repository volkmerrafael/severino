package com.volkmer.godinho.severino.resource.importador.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.persistence.EmbeddedId;

/**
 * Classe responsavel por obter ou setar informa��es a objetos atraves da Reflection
 * @author Stefan Horochovec <stefan@horochovec.com.br>
 * 
 */
public final class Reflection {
	
	private Reflection() {
		
	}
	
	
	/**
	 * Retorna o tipo do Atributo de uma classe
	 * @param classe 		Classe java
	 * @param atributo 		Nome do atributo
	 * @return String 
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	public static String tipoAtributo(final String classe, String atributo) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		Class virtualClass 	= Class.forName(classe);
		final Object objeto			= virtualClass.newInstance();
		Field[] cps					= objeto.getClass().getDeclaredFields();
		ArrayList<Field> campos		= new ArrayList<Field>();
		Field campo					= null;
		Field[] camposPK			= null;
		String retorno				= null;
		
		for(int i = 0; i < cps.length; i++) {
			campos.add(cps[i]);
		}
		
		while((virtualClass.getSuperclass() != null) && virtualClass.getSuperclass().getName().startsWith("br.inf.ids.")) {
			
			virtualClass = virtualClass.getSuperclass();
			
			cps = virtualClass.getDeclaredFields();
			
			for(int i = 0; i < cps.length; i++) {
				campos.add(cps[i]);
			}
		}
		
		
		for (int i = 0; i < campos.size(); i++) {
	        campo = campos.get(i);
	        if (campo.getName().equalsIgnoreCase(atributo)) {
	        	i = campos.size();
	        	retorno = campo.getType().getCanonicalName();
	        }
		}	
		
		if (retorno == null) {
			for (Integer i = 0; i < campos.size() ; i++) {
				campo = campos.get(i);
				if (campo.isAnnotationPresent(EmbeddedId.class)) {
					camposPK	= campo.getType().getDeclaredFields();
					i 			= campos.size();				
				}
			}		
		
		
			if (camposPK != null) {
				for (int i = 0; i < camposPK.length; i++) {
			        campo = camposPK[i];
			        if (campo.getName().equals(atributo)) {
			        	i = campos.size();
			        	retorno = campo.getType().getCanonicalName();
			        }
				}			
			}
		}
		
		
		
		if (retorno == null) {
			
			if (atributo.indexOf(".") > -1) {
				final String[] list 		= atributo.replace(".","@").split("@");
				atributo 					= list[1];
			}
			
			final Object campoAx 	= null;//DicionarioDados.getDicionarioPorCampo(atributo);
			
			/*
			
				0	Text 		- String
				1	Numeric 	- Integer 
				2	Decimal 	- Float
				3	Date		- Date
				4	CPF			- String
				5	CNPJ		- String
				6	CEP			- String
				7	Hora		- Timestamp
				8	PIS			- String
				9	Telefone	- String
				10	Lista		- Integer
				11	Check		- Integer
				12	Memorando	- String
				13	CNS			- String
				14	Cod Barras	- String
				15	Procedimento- String
				16	Prontuario	- String
				17	Data e Hora	- Timestamp
				18	Binario		- String
			
			*/
			/*
			switch ( campoAx.getCpotipo() ) {
				
				case ( 1 ):
				case ( 10 ):
				case ( 11 ):{ 
					retorno = "java.lang.Integer";
					break;
				}
				
				case ( 2 ): { 
					retorno = "java.lang.Float";
					break;
				}
				
				default: {
					retorno = "java.lang.String";
				}
			
			}
			*/
		} 
		
		return retorno;
	}

	/**
	 * Seta um valor a determinado atributo de um objeto
	 * @param atributo
	 * @param destino
	 * @param valor
	 */
	public static void setValor(final String atributo, final Object destino, final Object valor) {
		Reflection.setValor(atributo, destino, valor, valor.getClass().getName());
	}
	
	/**
	 * Seta um valor a determinado atributo de um objeto
	 * @param atributo
	 * @param destino
	 * @param valor
	 */
	public static void setValor(final String atributo, final Object destino, final Object valor, final Class<?> classe) {
		Reflection.setValor( atributo, destino, valor, classe.getName() );
	}
	
	/**
	 * Seta um valor a determinado atributo de um objeto
	 * @param atributo
	 * @param destino
	 * @param valor
	 * @param attClassName
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setValor(final String atributo, final Object destino, final Object valor, final String attClassName) {
				
		final Class targetClass 	= destino.getClass( );
		Method setter;
		try { 
			setter = targetClass.getMethod("set" + atributo, new Class[] { Class.forName(attClassName) } );
			setter.invoke(destino, new Object[] { valor });
		} catch (SecurityException e) {
			//e.printStackTrace();
		} catch (NoSuchMethodException e) {
			//e.printStackTrace();
		} catch (IllegalArgumentException e) {
			//e.printStackTrace();
		} catch (IllegalAccessException e) {
			//e.printStackTrace();
		} catch (InvocationTargetException e) {
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
		}
		
	}
	
	/**
	 * Obtem o valor de determinado atributo
	 * @param atributo
	 * @param objeto
	 * @return Object
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object getValor(final String atributo, final Object objeto) {
		
		Object	object 			= null;
		final Class targetClass	= objeto.getClass();
		Method	getter;
		
		try {
			getter = targetClass.getMethod("get" + atributo, null);
			object = (Object) getter.invoke(objeto, null);
		} catch (SecurityException e) {
			//e.printStackTrace();
		} catch (NoSuchMethodException e) {
			//e.printStackTrace();
		} catch (IllegalArgumentException e) {
			//e.printStackTrace();
		} catch (IllegalAccessException e) {
			//e.printStackTrace();
		} catch (InvocationTargetException e) {
			//e.printStackTrace();
		}
		
		return object;
		
	}
	
	/**
	 * 
	 * @param atributoGetter
	 * @param atributoSetter
	 * @param destino
	 * @param valor
	 */
	public static void setValorComposto(final String atributoGetter, final String atributoSetter, final Object destino, final Object valor) {
		
		final Object objeto	= getValor(atributoGetter, destino);
		setValor(atributoSetter, objeto, valor);
		
	}
	
	/**
	 * Obtem o valor de determinado atributo
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object getValorBean(String atributo, final Object objeto) throws SecurityException, NoSuchMethodException, 
									   IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Object	object 			= null;
		final Class targetClass	= objeto.getClass();
		Method	getter;
		getter = targetClass.getMethod("get" + atributo);
		object = (Object) getter.invoke(objeto);
		return object;
	}
	
	/**
	 * Obtem o valor de determinado atributo iginorando Erros.
	 * @param atributo
	 * @param objeto
	 * @return
	 */
	public static Object getValorNoException(String atributo, final Object objeto) {
		Object	object = null;
		try {
			object = (Object) getValorBean(atributo, objeto);
		} catch (Exception e) {
			return null;
		}
		return object;
	}
	
	/**
	 * Transforma propriedade valor do tipo String para a classe informada
	 * @param valor
	 * @param classe
	 * @return
	 */
	public static Object setTipoPropriedade(String valor, Class<?> classe){
		return setTipoPropriedade(valor, classe.getName());
	}
	
	/**
	 * Transforma propriedade valor do tipo String para a classe informada 
	 * @param valor
	 * @param classe
	 * @return
	 */
	public static Object setTipoPropriedade(String valor, String classe){
		if (classe.equals("java.lang.Integer")){
			Integer retorno = Integer.parseInt(valor);
			return retorno;
		}else if (classe.equals("java.lang.Short")){
			Short retorno = Short.parseShort(valor);
			return retorno;				
		}else if (classe.equals("java.lang.Float")){
			Float retorno = Float.parseFloat(valor);
			return retorno;						
		//}else if (classe.equals("java.sql.Date")){
		//Date retorno = IDSDateUtils.getDateOfString(valor);
		//return retorno;									
		//}
		//else if (classe.equals("java.sql.Timestamp")){
		///	Timestamp retorno = IDSDateUtils.getTimestampOfString(valor);
		//	return retorno;									
		}else{
			return valor;									
		}
	}
	
}
