package com.volkmer.godinho.severino.entity.mod_controleponto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="AnoMes", description="Ano e Mês")
@Table(
		indexes = { 
				@Index(name = "anomes_mes_ano_idx", columnList = "mes,ano")
		})
@Getter @Setter
@Entity
public class AnoMes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;
	
	@Column
	@NotNull
	@ApiModelProperty("Ano")
	private Integer ano;
	
	@Column
	@NotNull
	@ApiModelProperty("Mês")
	private Integer mes;
	
}