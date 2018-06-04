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
				@Index(name = "anomes_mes_ano_idx", columnList = "mes,ano")
		})
@Data
@Entity
public class AnoMes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoInfo(descricao="Código")
	private Long id;
	
	@Column
	@CampoInfo(descricao="Ano", obrigatorio=true)
	private Integer ano;
	
	@Column
	@CampoInfo(descricao="Mês", obrigatorio=true)
	private Integer mes;
	
}