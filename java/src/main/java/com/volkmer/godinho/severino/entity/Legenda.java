package com.volkmer.godinho.severino.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.volkmer.godinho.core.validacao.CampoInfo;

import lombok.Data;

@Table
@Data
@Entity
public class Legenda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoInfo(descricao="Código")
	private Long id;
	
	@Column(length=100)
	@CampoInfo(descricao="Nome", obrigatorio=true)
	private String nome;
	
	@Column(length=1)
	@CampoInfo(descricao="Sigla", obrigatorio=true)
	private String sigla;
	
}
