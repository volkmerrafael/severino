package com.volkmer.godinho.severino.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.volkmer.godinho.core.mapperjson.LocalDateTimeDeserializer;
import com.volkmer.godinho.core.mapperjson.LocalDateTimeSerializer;
import com.volkmer.godinho.core.validacao.CampoInfo;
import com.volkmer.godinho.severino.resource.questao.QuestaoStatus;
import com.volkmer.godinho.severino.resource.questao.QuestaoTipo;

import lombok.Data;

@Table(
		indexes = { 
				@Index(name = "questao_usuario_idx", columnList = "usuarioid")
		})
@Data
@Entity
public class Questao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoInfo(descricao="Código")
	private Long id;

	@Column(length=500)
	@CampoInfo(descricao="Descrição", obrigatorio=false)
	private String descricao;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="usuarioId", foreignKey=@ForeignKey(name="fk_questao_usuario"))
	private Usuario usuario;
	
	@Column
	@CampoInfo(descricao="Data e Hora do Cadastro", obrigatorio=true)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime data_hora;

	@Column
	@Enumerated(EnumType.STRING)
	@CampoInfo(descricao="Tipo", obrigatorio=true)
	private QuestaoTipo tipo;
	
	@Column
	@Enumerated(EnumType.STRING)
	@CampoInfo(descricao="Status", obrigatorio=true)
	private QuestaoStatus status;

}