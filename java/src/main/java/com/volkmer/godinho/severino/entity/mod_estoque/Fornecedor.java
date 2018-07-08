package com.volkmer.godinho.severino.entity.mod_estoque;

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

@ApiModel(value="Fornecedor", description="Fornecedor")
@Table
@Getter @Setter
@Entity
public class Fornecedor {
	
	@Id
	@ApiModelProperty("Código")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=100)
	@NotNull
	@ApiModelProperty("Razão Social")
	private String razaosocial;
	
	@Column(length=100)
	@NotNull
	@ApiModelProperty("CNPJ")
	private String cnpj;

}
