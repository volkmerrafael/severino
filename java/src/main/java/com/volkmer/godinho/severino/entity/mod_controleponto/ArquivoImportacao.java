package com.volkmer.godinho.severino.entity.mod_controleponto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="ArquivoImportacao", description="Arquivo Importação")
@Table
@Getter @Setter
@Entity
public class ArquivoImportacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;
	
	@Column
	@ApiModelProperty("Anexo")
	private byte[] anexo;
	
}