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

@ApiModel(value="Legenda", description="Legenda")
@Table(
		indexes = { 
				@Index(name = "legenda_sigla_idx", columnList = "sigla")
		})
@Getter @Setter
@Entity
public class Legenda {
	
	@Id
	@ApiModelProperty("Código")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=100)
	@NotNull
	@ApiModelProperty("Nome")
	private String nome;
	
	@Column(length=1)
	@NotNull
	@ApiModelProperty("Sigla")
	private String sigla;
	
}
