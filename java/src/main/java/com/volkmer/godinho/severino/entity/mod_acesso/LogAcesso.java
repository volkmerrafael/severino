package com.volkmer.godinho.severino.entity.mod_acesso;

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
import com.volkmer.godinho.severino.resource.mod_acesso.logacesso.TipoLogAcesso;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="LogAcesso", description="Log de Acesso")
@Table(
		indexes = { 
				@Index(name = "logacesso_usuario_idx", columnList = "usuarioid")
		})
@Getter @Setter
@Entity
public class LogAcesso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="usuarioId", foreignKey=@ForeignKey(name="fk_ponto_usuario"))
	private Usuario usuario;
	
	@Column
	@Enumerated(EnumType.STRING)
	@ApiModelProperty("Tipo")
	private TipoLogAcesso tipo; 
	
	@Column
	@ApiModelProperty("Data e Hora do Cadastro")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime data_hora;
	
	@Column
	@ApiModelProperty("IP")
	private String ip;
	
	@Column
	@ApiModelProperty("Método")
	private String metodo;
	
	@Column
	@ApiModelProperty("Conteúdo")
	private String conteudo;
	
}