package com.volkmer.godinho.severino.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
		this.vString = "Não viva para que a sua presença seja notada, mas para que a sua falta seja sentida.\nBob Marley";
		this.vLocalDate = LocalDate.of(1986, 9, 17);
		
	}

	public Integer getvInteger() {
		return vInteger;
	}

	public void setvInteger(Integer vInteger) {
		this.vInteger = vInteger;
	}

	public Long getvLong() {
		return vLong;
	}

	public void setvLong(Long vLong) {
		this.vLong = vLong;
	}

	public Boolean getvBooleanTrue() {
		return vBooleanTrue;
	}

	public void setvBooleanTrue(Boolean vBooleanTrue) {
		this.vBooleanTrue = vBooleanTrue;
	}

	public Boolean getvBooleanFalse() {
		return vBooleanFalse;
	}

	public void setvBooleanFalse(Boolean vBooleanFalse) {
		this.vBooleanFalse = vBooleanFalse;
	}

	public Double getvDouble() {
		return vDouble;
	}

	public void setvDouble(Double vDouble) {
		this.vDouble = vDouble;
	}

	public Float getvFloat() {
		return vFloat;
	}

	public void setvFloat(Float vFloat) {
		this.vFloat = vFloat;
	}

	public BigDecimal getvBigDecimal() {
		return vBigDecimal;
	}

	public void setvBigDecimal(BigDecimal vBigDecimal) {
		this.vBigDecimal = vBigDecimal;
	}

	public String getvString() {
		return vString;
	}

	public void setvString(String vString) {
		this.vString = vString;
	}

	public LocalDate getvLocalDate() {
		return vLocalDate;
	}

	public void setvLocalDate(LocalDate vLocalDate) {
		this.vLocalDate = vLocalDate;
	}
	
}
