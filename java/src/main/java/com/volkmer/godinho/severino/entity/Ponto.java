package com.volkmer.godinho.severino.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.volkmer.godinho.core.validacao.CampoInfo;
import com.volkmer.godinho.severino.resource.ponto.PontoStatus;

import lombok.Getter;
import lombok.Setter;

@Table(
		indexes = { 
				@Index(name = "ponto_usuario_data_idx", columnList = "usuarioid,data"),
				@Index(name = "ponto_data_idx", columnList = "data")
		})
@Getter @Setter
@Entity
public class Ponto {

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
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="legendaId", foreignKey=@ForeignKey(name="fk_ponto_legenda"))
	private Legenda legenda;
	
	@Column
	@CampoInfo(descricao="Data", obrigatorio=false)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate data;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="diasemanaId", foreignKey=@ForeignKey(name="fk_ponto_diasemana"))
	private DiaSemana diasemana;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="jornadaId", foreignKey=@ForeignKey(name="fk_ponto_jornada"))
	private Jornada jornada;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="justificativaId", foreignKey=@ForeignKey(name="fk_ponto_justificativa"))
	private Justificativa justificativa;
	
	@Column(length=6)
	@CampoInfo(descricao="Horário de Entrada 1", obrigatorio=false)
	private String entrada1;
	
	@Column(length=6)
	@CampoInfo(descricao="Horário de Saída 1", obrigatorio=false)
	
	private String saida1;

	@Column(length=6)
	@CampoInfo(descricao="Horário de Entrada 2", obrigatorio=false)
	private String entrada2;
	
	@Column(length=6)
	@CampoInfo(descricao="Horário de Saída 2", obrigatorio=false)
	private String saida2;
	
	@Column(length=6)
	@CampoInfo(descricao="Horário de Entrada 3", obrigatorio=false)
	private String entrada3;

	@Column(length=6)
	@CampoInfo(descricao="Horário de Saída 3", obrigatorio=false)
	private String saida3;
	
	@Column(length=6)
	@CampoInfo(descricao="Horário de Entrada 4", obrigatorio=false)
	private String entrada4;
	
	@Column(length=6)
	@CampoInfo(descricao="Horário de Saída 4", obrigatorio=false)
	private String saida4;
	
	@Column(length=150)
	@CampoInfo(descricao="Horas Trabalhadas Diurno", obrigatorio=false)
	private String trabalhadas_diurno;

	@Column(length=150)
	@CampoInfo(descricao="Horas Trabalhadas Noturno", obrigatorio=false)
	private String trabalhadas_noturno;
	
	@Column(length=150)
	@CampoInfo(descricao="Horas Extra Diurno", obrigatorio=false)
	private String extra_diurno;
	
	@Column(length=150)
	@CampoInfo(descricao="Horas Extra Noturno", obrigatorio=false)
	private String extra_noturno;

	@Column(length=150)
	@CampoInfo(descricao="Horas Falta Diurno", obrigatorio=false)
	private String faltas_diurno;

	@Column(length=150)
	@CampoInfo(descricao="Horas Falta Noturno", obrigatorio=false)
	private String faltas_noturno;

	@Column
	@Enumerated(EnumType.STRING)
	@CampoInfo(descricao="Status", obrigatorio=true)
	private PontoStatus status;

	@Column
	@CampoInfo(descricao="Minutos Débito", obrigatorio=false)
	private Integer minutos_debito;

	@Column
	@CampoInfo(descricao="Minutos Crédito", obrigatorio=false)
	private Integer minutos_credito;

	@Column
	@CampoInfo(descricao="Minutos Abono", obrigatorio=false)
	private Integer minutos_abono;
	
	@Column
	@CampoInfo(descricao="Minutos Trabalhados", obrigatorio=false)
	private Integer minutos_trabalhados;
	
	@Column(length=150)
	@CampoInfo(descricao="Observação", obrigatorio=false)
	private String observacao;

	@OneToMany(mappedBy = "ponto", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Worklog> worklogs;
	
}