package com.volkmer.godinho.severino.entity.mod_geral.usuario.dadosbancarios;

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

@ApiModel(value="Banco", description="Banco")
@Table
@Getter @Setter
@Entity
public class Banco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;
	
	@NotNull
	@Column(length=300)
	@ApiModelProperty("Descrição")
	private String descricao;

	@NotNull
	@Column
	@ApiModelProperty("Identificador")
	private Long identificador;
	
}
