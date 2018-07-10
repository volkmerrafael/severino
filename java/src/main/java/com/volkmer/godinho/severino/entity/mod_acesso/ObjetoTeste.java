package com.volkmer.godinho.severino.entity.mod_acesso;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("ObjetoTeste")
@Data
public class ObjetoTeste {
	
	private Integer vInteger;
	private Long vLong;
	private Boolean vBooleanTrue;
	private Boolean vBooleanFalse;
	private Double vDouble;
	private Float vFloat;
	private BigDecimal vBigDecimal;
	private String vString;
	private LocalDate vLocalDate;
	
	public void criar() {
		
		this.vInteger = 1;
		this.vLong = 2l;
		this.vBooleanTrue = true;
		this.vBooleanFalse = false;
		this.vDouble = 1.2;
		this.vFloat = 2.33f;
		this.vBigDecimal = new BigDecimal("3.444");
		this.vString = "Teste objeto de teste";
		this.vLocalDate = LocalDate.of(1986, 9, 17);
		
	}
	
}
