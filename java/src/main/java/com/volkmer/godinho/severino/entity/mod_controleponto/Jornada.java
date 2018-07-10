package com.volkmer.godinho.severino.entity.mod_controleponto;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.volkmer.godinho.core.mapperjson.LocalTimeDeserializer;
import com.volkmer.godinho.core.mapperjson.LocalTimeSerializer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Jornada", description="Jornada")
@Table(
		indexes = { 
				@Index(name = "jornada_horas_idx", columnList = "periodo_jornada,horas")
		})
@Getter @Setter
@Entity
public class Jornada {
	
	@Id
	@ApiModelProperty("Código")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	@ApiModelProperty("Periodo da Jornada")
	private String periodo_jornada;
	
	@Column
	@ApiModelProperty("Horas")
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	@JsonSerialize(using = LocalTimeSerializer.class)
	private LocalTime horas;
	
}