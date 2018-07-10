package com.volkmer.godinho.severino.entity.mod_geral;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.volkmer.godinho.core.mapperjson.LocalDateTimeDeserializer;
import com.volkmer.godinho.core.mapperjson.LocalDateTimeSerializer;
import com.volkmer.godinho.core.validacao.CampoInfo;
import com.volkmer.godinho.severino.resource.mod_geral.conexao.ConexaoSistema;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="Conexao", description="Conexão")
@Table
@Data
@Entity
public class Conexao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;
	
	@Column(length=30)
	@NotNull
	@ApiModelProperty("IP (Host) do Banco")
	private String ip;
	
	@Column(length=64)
	@NotNull
	@ApiModelProperty("Porta do Banco")
	private String porta;
	
	@Column(length=128)
	@NotNull
	@ApiModelProperty("Nome do Banco")
	private String banco;

	@Column(length=128)
	@NotNull
	@ApiModelProperty("Usuário do Banco")
	private String usuario;
	
	@Column(length=128)
	@NotNull
	@ApiModelProperty("Senha do Banco")
	@CampoInfo(descricao="Senha", obrigatorio=true)
	private String senha;
	
	@Column
	@NotNull
	@ApiModelProperty("Data e Horas da Gravação do Registro")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using =  LocalDateTimeSerializer.class)
	private LocalDateTime data;
	
	@Column
	@NotNull
	@ApiModelProperty("Conexão Sistema")
	@Enumerated(EnumType.STRING)
	private ConexaoSistema conexao_sistema;
}
