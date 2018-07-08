package com.volkmer.godinho.severino.entity.mod_controleponto;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.entity.mod_jira.Issue;
import com.volkmer.godinho.severino.resource.mod_controleponto.ponto.PontoStatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Ponto", description="Ponto")
@Table(
		indexes = { 
				@Index(name = "ponto_usuario_data_idx", columnList = "usuarioid,data"),
				@Index(name = "ponto_data_idx", columnList = "data")
		})
@Getter @Setter
@Entity
public class Ponto {

	@Id
	@ApiModelProperty("Código")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty("Usuário")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="usuarioId", foreignKey=@ForeignKey(name="fk_ponto_usuario"))
	private Usuario usuario;

	@ApiModelProperty("Ano/Mês")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="anomesId", foreignKey=@ForeignKey(name="fk_ponto_anomes"))
	private AnoMes anomes;

	@ApiModelProperty("Importação")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="importacaoId", foreignKey=@ForeignKey(name="fk_ponto_importacao"))
	private Importacao importacao;
	
	@ApiModelProperty("Legenda")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="legendaId", foreignKey=@ForeignKey(name="fk_ponto_legenda"))
	private Legenda legenda;
	
	@Column
	@ApiModelProperty("Data")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate data;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@ApiModelProperty("Dia da Semana")
	@JoinColumn(name="diasemanaId", foreignKey=@ForeignKey(name="fk_ponto_diasemana"))
	private DiaSemana diasemana;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@ApiModelProperty("Jornada")
	@JoinColumn(name="jornadaId", foreignKey=@ForeignKey(name="fk_ponto_jornada"))
	private Jornada jornada;

	@Column(length=350)
	@ApiModelProperty("Justificativa")
	private String justificativa;
	
	@Column(length=6)
	@ApiModelProperty("Horário de Entrada 1")
	private String entrada1;
	
	@Column(length=6)
	@ApiModelProperty("Horário de Saída 1")
	private String saida1;

	@Column(length=6)
	@ApiModelProperty("Horário de Entrada 2")
	private String entrada2;
	
	@Column(length=6)
	@ApiModelProperty("Horário de Saída 2")
	private String saida2;
	
	@Column(length=6)
	@ApiModelProperty("Horário de Entrada 3")
	private String entrada3;

	@Column(length=6)
	@ApiModelProperty("Horário de Saída 3")
	private String saida3;
	
	@Column(length=6)
	@ApiModelProperty("Horário de Entrada 4")
	private String entrada4;
	
	@Column(length=6)
	@ApiModelProperty("Horário de Saída 4")
	private String saida4;
	
	@Column(length=6)
	@ApiModelProperty("Horas Trabalhadas Diurno")
	private String trabalhadas_diurno;

	@Column(length=6)
	@ApiModelProperty("Horas Trabalhadas Noturno")
	private String trabalhadas_noturno;
	
	@Column(length=6)
	@ApiModelProperty("Horas Extra Diurno")
	private String extra_diurno;
	
	@Column(length=6)
	@ApiModelProperty("Horas Extra Noturno")
	private String extra_noturno;

	@Column(length=6)
	@ApiModelProperty("Horas Falta Diurno")
	private String faltas_diurno;

	@Column(length=6)
	@ApiModelProperty("Horas Falta Noturno")
	private String faltas_noturno;

	@Column
	@ApiModelProperty("Status")
	@Enumerated(EnumType.STRING)
	private PontoStatus status;

	@Column
	@ApiModelProperty("Minutos Débito")
	private Integer minutos_debito;

	@Column
	@ApiModelProperty("Minutos Crédito")
	private Integer minutos_credito;

	@Column
	@ApiModelProperty("Minutos Abono")
	private Integer minutos_abono;
	
	@Column
	@ApiModelProperty("Minutos Trabalhados")
	private Integer minutos_trabalhados;
	
	@Column(length=150)
	@ApiModelProperty("Observação")
	private String observacao;

	@ApiModelProperty("Issues")
	@OneToMany(mappedBy = "ponto", fetch = FetchType.EAGER) //, cascade = CascadeType.ALL
	private List<Issue> issues;
	
	@ApiModelProperty("Worklog Diário")
	@Transient 
	private BigDecimal worklog_diario;
	
}