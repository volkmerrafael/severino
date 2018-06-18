package com.volkmer.godinho.severino.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.volkmer.godinho.core.mapperjson.LocalDateTimeDeserializer;
import com.volkmer.godinho.core.mapperjson.LocalDateTimeSerializer;
import com.volkmer.godinho.core.validacao.CampoInfo;

import lombok.Data;

@Table
@Data
@Entity
public class Justificativa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoInfo(descricao="Código")
	private Long id;

	@Column(length=500)
	@CampoInfo(descricao="Descrição", obrigatorio=false)
	private String descricao;
	
	@Column
	@CampoInfo(descricao="Data e Hora", obrigatorio=false)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime data_hora;

}