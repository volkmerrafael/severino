package com.volkmer.godinho.severino.entity;

import java.time.LocalTime;

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
				@Index(name = "jornada_jornada_idx", columnList = "periodo_jornada,jornada")
		})
@Data
@Entity
public class Jornada {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoInfo(descricao="Código")
	private Long id;
	
	@Column()
	@CampoInfo(descricao="Periodo da Jornada", obrigatorio=true)
	private String periodo_jornada;
	
	@Column
	@CampoInfo(descricao="Jornada", obrigatorio=true)
	private LocalTime jornada;
	
}