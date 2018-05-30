package com.volkmer.godinho.severino.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.volkmer.godinho.core.validacao.CampoInfo;

import lombok.Data;

@Table(
		indexes = { 
				@Index(name = "departamento_nome_idx", columnList = "nome")
		})
@Data
@Entity
public class Departamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoInfo(descricao="Código")
	private Long id;
	
	@Column(length=200)
	@CampoInfo(descricao="Nome", obrigatorio=true)
	private String nome;
	
}
