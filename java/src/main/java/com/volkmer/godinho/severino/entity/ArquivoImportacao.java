package com.volkmer.godinho.severino.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.volkmer.godinho.core.validacao.CampoInfo;

import lombok.Getter;
import lombok.Setter;

@Table
@Entity
public class ArquivoImportacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoInfo(descricao="Código")
	@Getter @Setter
	private Long id;
	
	@Column
	@CampoInfo(descricao="Anexo", obrigatorio=true)
	@Getter @Setter
	private byte[] anexo;
	
}