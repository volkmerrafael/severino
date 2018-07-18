package com.volkmer.godinho.severino.entity.mod_geral.endereco;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Municipio", description="Município")
@Table
@Getter @Setter
@Entity
public class Municipio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;

	@Column
	@NotNull
	@ApiModelProperty("Código no IBGE")
	private Long codigo_ibge;
	
	@Column(length=150)
	@NotNull
	@ApiModelProperty("Nome")
	private String nome;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ufId", foreignKey=@ForeignKey(name="fk_municipio_uf"))
	private Uf uf;
			
}
