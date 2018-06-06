package com.volkmer.godinho.severino.entity;

import java.time.LocalTime;

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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.volkmer.godinho.core.mapperjson.LocalTimeDeserializer;
import com.volkmer.godinho.core.mapperjson.LocalTimeSerializer;
import com.volkmer.godinho.core.validacao.CampoInfo;

import lombok.Data;

@Table
@Data
@Entity
public class ControleHoras {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoInfo(descricao="Código")
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="usuarioId", foreignKey=@ForeignKey(name="fk_ponto_usuario"))
	private Usuario usuario;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="anomesId", foreignKey=@ForeignKey(name="fk_ponto_anomes"))
	private AnoMes anomes;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="importacaoId", foreignKey=@ForeignKey(name="fk_ponto_importacao"))
	private Importacao importacao;
		
	@Column
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	@JsonSerialize(using = LocalTimeSerializer.class)
	@CampoInfo(descricao="Horas Crédito", obrigatorio=false)
	private LocalTime horas_credito;
	
	@Column
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	@JsonSerialize(using = LocalTimeSerializer.class)
	@CampoInfo(descricao="Horas Saldo Mês", obrigatorio=false)
	private LocalTime horas_saldo;
	
	@Column
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	@JsonSerialize(using = LocalTimeSerializer.class)
	@CampoInfo(descricao="Horas Débito", obrigatorio=false)
	private LocalTime horas_debito;
	
	@Column
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	@JsonSerialize(using = LocalTimeSerializer.class)
	@CampoInfo(descricao="Banco de Horas", obrigatorio=false)
	private LocalTime banco_de_horas;

}