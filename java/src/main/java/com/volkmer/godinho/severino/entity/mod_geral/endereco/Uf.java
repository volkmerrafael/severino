package com.volkmer.godinho.severino.entity.mod_geral.endereco;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Uf", description="UF")
@Table
@Getter @Setter
@Entity
public class Uf {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;
	
	@Column(length=150)
	@NotNull
	@ApiModelProperty("Nome")
	private String nome;
	
	@Column(length=2)
	@NotNull
	@ApiModelProperty("Sigla")
	private String sigla;
			
}
