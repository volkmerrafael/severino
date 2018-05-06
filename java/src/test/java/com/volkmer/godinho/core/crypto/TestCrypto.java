package com.volkmer.godinho.core.crypto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestCrypto {

	private Crypto crypto;

	@Before
	public void before() {
		this.crypto = new Crypto();
	}
	
	@Test
	public void testeNull() {
		
		try {
			String texto = this.crypto.criptografar(null);
			assertNotNull(texto);
			assertTrue("Deveria ter vindo Exception", false);
		} catch (Exception e) {
			assertTrue(true);
		}
		
	}
	
	@Test
	public void testeVazio() {
		
		try {
			String texto = this.crypto.criptografar("");
			assertNotNull(texto);
			assertTrue("Deveria ter vindo Exception", false);
		} catch (Exception e) {
			assertTrue(true);
		}
		
	}
	
	@Test
	public void correto() {
		
		try {
			String texto = this.crypto.criptografar("rafael.volkmer1@gmail.com");
			assertNotNull(texto);
			assertEquals(64, texto.length());
		} catch (Exception e) {
			assertTrue("Exception ao executar", false);
		}
		
	}
	
}
