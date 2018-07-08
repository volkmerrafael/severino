package com.volkmer.godinho.severino.entity.mod_controleponto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.volkmer.godinho.core.mapperjson.LocalDateTimeDeserializer;
import com.volkmer.godinho.core.mapperjson.LocalDateTimeSerializer;
import com.volkmer.godinho.core.mapperjson.LocalTimeDeserializer;
import com.volkmer.godinho.core.mapperjson.LocalTimeSerializer;
import com.volkmer.godinho.core.validacao.CampoInfo;
import com.volkmer.godinho.severino.resource.mod_controleponto.importacao.ImportacaoStatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Importacao", description="Importação")
@Table
@Getter @Setter
@Entity
public class Importacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código") 
	private Long id;
	
	@Column(length=100)
	@NotNull
	@ApiModelProperty("Nome")
	private String nome;
	
	@Column(length=4)
	@NotNull
	@ApiModelProperty("Extensão")
	private String extensao;
	
	@Column
	@NotNull
	@ApiModelProperty("Tamanho")
	private Integer tamanho;

	@Column
	@ApiModelProperty("Início Período")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate inicio_periodo;

	@Column
	@ApiModelProperty("Tamanho")
	@CampoInfo(descricao="Final Período", obrigatorio=true)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate final_periodo;

	@Column
	@ApiModelProperty("Data e Hora Importação")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime data_hora_importacao;

	@Column
	@ApiModelProperty("Usuários Importados")
	private Integer quantidade_usuario;

	@Column
	@ApiModelProperty("Usuários com Débito Banco de Horas")
	private Integer usuario_com_debito_banco;

	@Column
	@ApiModelProperty("Usuários com Crédito Banco de Horas")
	private Integer usuario_com_credito_banco;

	@Column
	@ApiModelProperty("Usuários com Marcação Incorreta")
	private Integer usuario_com_marcacao_incorreta;

	@Column
	@ApiModelProperty("Usuários sem Pendências")
	private Integer usuario_sem_pendencias;

	@Column
	@ApiModelProperty("Tempo de Importacão")
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	@JsonSerialize(using = LocalTimeSerializer.class)
	private LocalTime tempo_importacao;
	
	@Column
	@ApiModelProperty("Tempo de Processamento do Arquivo")
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	@JsonSerialize(using = LocalTimeSerializer.class)
	private LocalTime tempo_processamento;
	
	@Column
	@ApiModelProperty("Status")
	@Enumerated(EnumType.STRING)
	private ImportacaoStatus status;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="arquivoimportacaoId", foreignKey=@ForeignKey(name="fk_arquivoimportacao_importacao"))
	private ArquivoImportacao arquivoimportacao;
	
}
