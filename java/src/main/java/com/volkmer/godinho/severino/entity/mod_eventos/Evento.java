package com.volkmer.godinho.severino.entity.mod_eventos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.volkmer.godinho.core.mapperjson.LocalDateTimeDeserializer;
import com.volkmer.godinho.core.mapperjson.LocalDateTimeSerializer;
import com.volkmer.godinho.core.util.enumeration.SimNao;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Evento", description="Evento")
@Table
@Getter @Setter
@Entity
public class Evento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;
	
	@Column(length=500)
	@NotNull
	@ApiModelProperty("Descrição")
	private String descricao;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tipoeventoId", foreignKey=@ForeignKey(name="fk_evento_tipoevento"))
	private TipoEvento tipoevento;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="usuarioId", foreignKey=@ForeignKey(name="fk_evento_usuario"))
	private Usuario usuario;
	
	@Column
	@NotNull
	@ApiModelProperty("Data e Hora Inicio Evento")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using =  LocalDateTimeSerializer.class)
	private LocalDateTime data_hora_inicio;
	
	@Column
	@NotNull
	@ApiModelProperty("Data e Hora Termino Evento")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using =  LocalDateTimeSerializer.class)
	private LocalDateTime data_hora_termino;
	
	@Column
	@NotNull
	@ApiModelProperty("Data e Hora Limite Confirmação Evento")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using =  LocalDateTimeSerializer.class)
	private LocalDateTime data_hora_limite_confirmacao;
	
	@Column
	@NotNull
	@ApiModelProperty("Duração")
	private Integer duracao;
	
	@Column
	@NotNull
	@ApiModelProperty("Limite de Convidados")
	private Integer limite_convidados;
	
	@Column
	@NotNull
	@ApiModelProperty("Limite de Vagas")
	private Integer limite_vagas;
	
	@Column
	@ApiModelProperty("Confirmados")
	private Integer confirmados;
	
	@Column
	@ApiModelProperty("Permite Convidados")
	@Enumerated(EnumType.STRING)
	private SimNao permite_convidados;
	
	@Column
	@ApiModelProperty("Evento Publico")
	@Enumerated(EnumType.STRING)
	private SimNao evento_publico;
	
	@Column(precision = 11, scale = 8)
	@ApiModelProperty("Longitude")
	private BigDecimal longitude;

	@Column
	@ApiModelProperty("Informar Localização")
	@Enumerated(EnumType.STRING)
	private SimNao informar_localizacao;
	
	@Column(precision = 11, scale = 8)
	@ApiModelProperty("Latitude")
	private BigDecimal latitude;
	
	@Column
	@ApiModelProperty("Favorito")
	@Enumerated(EnumType.STRING)
	private SimNao favorito;
	
	@ApiModelProperty("Participantes")
	@OneToMany(mappedBy = "evento", fetch = FetchType.EAGER) //, cascade = CascadeType.ALL
	private List<ParticipanteEvento> participantes;

}
