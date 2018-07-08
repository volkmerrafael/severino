package com.volkmer.godinho.severino.entity.mod_geral.usuario;

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

@ApiModel(value="Empresa", description="Empresa")
@Table(
		indexes = { 
				@Index(name = "empresa_razaosocial_idx", columnList = "razao_social")
		})
@Getter @Setter
@Entity
public class Empresa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;
	
	@Column(length=300)
	@NotNull
	@ApiModelProperty("Razão Social")
	private String razao_social;
	
	@Column(length=200)
	@NotNull
	@ApiModelProperty("C.N.P.J. (Cadastro Nacional da Pessoa Jurídica)")
	private String cnpj;

	@Column(length=200)
	@NotNull
	@ApiModelProperty("Endereço")
	private String endereco;
	
	@Column(length=200)
	@NotNull
	@ApiModelProperty("Cidade")
	private String cidade;
	
	@Column(length=200)
	@NotNull
	@ApiModelProperty("Unidade e Federação (UF)")
	private String uf;
	
}
