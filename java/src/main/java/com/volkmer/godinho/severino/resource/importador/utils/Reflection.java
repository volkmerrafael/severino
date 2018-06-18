package com.volkmer.godinho.severino.resource.importador.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Classe responsavel por obter ou setar informações a objetos atraves da Reflection
 */
public final class Reflection {
	
	private Reflection() {
		
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
		} catch (NoSuchMethodException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		} catch (ClassNotFoundException e) {
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
		
		try {
			Method getter = targetClass.getMethod("get" + atributo, null);
			object = (Object) getter.invoke(objeto, null);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		
		return object;
		
	}
	
}
