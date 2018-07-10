package com.volkmer.godinho.severino.entity.mod_geral;

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
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.resource.mod_geral.questao.QuestaoStatus;
import com.volkmer.godinho.severino.resource.mod_geral.questao.QuestaoTipo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Questao", description="Questão")
@Table(
		indexes = { 
				@Index(name = "questao_usuario_idx", columnList = "usuarioid")
		})
@Getter @Setter
@Entity
public class Questao {

	@Id
	@ApiModelProperty("Código")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length=500)
	@ApiModelProperty("Descrição")
	private String descricao;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="usuarioId", foreignKey=@ForeignKey(name="fk_questao_usuario"))
	private Usuario usuario;
	
	@Column
	@ApiModelProperty("Data e Hora do Cadastro")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime data_hora;

	@Column
	@ApiModelProperty("Tipo")
	@Enumerated(EnumType.STRING)
	private QuestaoTipo tipo;
	
	@Column
	@ApiModelProperty("Status")
	@Enumerated(EnumType.STRING)
	private QuestaoStatus status;

}