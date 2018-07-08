package com.volkmer.godinho.severino.entity.mod_acesso;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.volkmer.godinho.core.mapperjson.LocalDateTimeDeserializer;
import com.volkmer.godinho.core.mapperjson.LocalDateTimeSerializer;
import com.volkmer.godinho.severino.resource.mod_acesso.acesso.AcessoTipo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Acesso", description="Acesso")
@Table
@Getter @Setter
@Entity
public class Acesso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;
	
	@Column(length=30)
	@ApiModelProperty("Nome")
	private String nomeacesso;
	
	@Column(length=64)
	@ApiModelProperty("Senha")
	private String senha;
	
	@Column
	@ApiModelProperty("Data")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using =  LocalDateTimeSerializer.class)
	private LocalDateTime data;
	
	@Column(length=128)
	@ApiModelProperty("Chave do Usuário")
	private String token;
	
	@Column
	@ApiModelProperty("Tipo do Acesso")
	@Enumerated(EnumType.STRING)
	private AcessoTipo tipo;
	
}
