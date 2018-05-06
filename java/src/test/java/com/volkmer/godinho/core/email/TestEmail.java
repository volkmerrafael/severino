package com.volkmer.godinho.core.email;

import org.junit.Assert;

public class TestEmail {

	//@Test
	public void teste() {
		
		try {
			
			new Email().sendEmail();
			Assert.assertTrue(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
