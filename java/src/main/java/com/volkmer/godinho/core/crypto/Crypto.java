package com.volkmer.godinho.core.crypto;

import java.security.MessageDigest;

public class Crypto {

	public String criptografar(String value) throws Exception {

		value = value.trim();
		if (value.length()==0) {
			throw new Exception("Valor vazio");
		}
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(value.getBytes());
		
		return bytesToHex(md.digest());

	}

	private String bytesToHex(byte[] bytes) {
		
		StringBuffer result = new StringBuffer();
		for (byte b : bytes) {
			result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		}
		return result.toString();
		
	}

}
