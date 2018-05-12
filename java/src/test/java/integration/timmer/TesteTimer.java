package integration.timmer;

import static org.junit.Assert.assertTrue;

import java.time.LocalTime;

import org.junit.Test;

import com.volkmer.godinho.core.timmer.ControleTempoDeExecucao;

public class TesteTimer {
		
	@Test
	public void testeTimmer() {
		
		ControleTempoDeExecucao cte = new ControleTempoDeExecucao();
		
		cte.inicio();
		
		LocalTime locTime = cte.fim();
		
		if (locTime!=null) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
		
	}
	
}
