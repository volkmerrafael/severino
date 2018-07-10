package com.volkmer.godinho.severino.entity.mod_eventos;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.volkmer.godinho.core.util.SimNao;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="ParticipanteEvento", description="Participante do Evento")
@Table
@Getter @Setter
@Entity
public class ParticipanteEvento {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@ApiModelProperty("Código")
		private Long id;

		@ApiModelProperty("Usuário")
		@ManyToOne(fetch=FetchType.EAGER)
		@JoinColumn(name="usuarioId", foreignKey=@ForeignKey(name="fk_ponto_usuario"))
		private Usuario usuario;
		
		@Column
		@ApiModelProperty("Compareceu")
		@Enumerated(EnumType.STRING)
		private SimNao compareceu;
		
		@Column(length=500)
		@NotNull
		@ApiModelProperty("Observação")
		private String observacao;
		
		@ManyToOne(fetch=FetchType.EAGER)
		@JsonIgnore	
		@JoinColumn(name="eventoId", foreignKey=@ForeignKey(name="fk_ponto_evento"))
		private Evento evento;
		
}