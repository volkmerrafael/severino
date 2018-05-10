package com.volkmer.godinho.severino.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

public class ObjetoTeste {
	
	@Getter @Setter private Integer vInteger;
	@Getter @Setter private Long vLong;
	@Getter @Setter private Boolean vBooleanTrue;
	@Getter @Setter private Boolean vBooleanFalse;
	@Getter @Setter private Double vDouble;
	@Getter @Setter private Float vFloat;
	@Getter @Setter private BigDecimal vBigDecimal;
	@Getter @Setter private String vString;
	@Getter @Setter private LocalDate vLocalDate;
	
	public void criar() {
		
		this.vInteger = 1;
		this.vLong = 2l;
		this.vBooleanTrue = true;
		this.vBooleanFalse = false;
		this.vDouble = 1.2;
		this.vFloat = 2.33f;
		this.vBigDecimal = new BigDecimal("3.444");
		this.vString = "Não viva para que a sua presença seja notada, mas para que a sua falta seja sentida.\nBob Marley";
		this.vLocalDate = LocalDate.of(1986, 9, 17);
		
	}
	
}
