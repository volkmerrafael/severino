package com.volkmer.godinho.severino.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.volkmer.godinho.core.validacao.CampoInfo;

@Table
@Entity
public class Ponto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoInfo(descricao="Código")
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="usuarioId", foreignKey=@ForeignKey(name="fk_ponto_usuario"))
	private Usuario usuario;

	@Column(length=150)
	@CampoInfo(descricao="Data", obrigatorio=false)
	private String data;
	
	@Column(length=150)
	@CampoInfo(descricao="Dia da Semana", obrigatorio=false)
	private String diasemana;
	
	@Column(length=1500)
	@CampoInfo(descricao="Jornada", obrigatorio=false)
	private String jornada;

	@Column(length=150)
	@CampoInfo(descricao="Legenda", obrigatorio=false)
	private String legenda;

	@Column(length=150)
	@CampoInfo(descricao="Horário de Entrada 1", obrigatorio=false)
	private String entrada1;
	
	@Column(length=150)
	@CampoInfo(descricao="Horário de Saída 1", obrigatorio=false)
	private String saida1;

	@Column(length=150)
	@CampoInfo(descricao="Horário de Entrada 2", obrigatorio=false)
	private String entrada2;
	
	@Column(length=150)
	@CampoInfo(descricao="Horário de Saída 2", obrigatorio=false)
	private String saida2;
	
	@Column(length=150)
	@CampoInfo(descricao="Horário de Entrada 3", obrigatorio=false)
	private String entrada3;

	@Column(length=150)
	@CampoInfo(descricao="Horário de Saída 3", obrigatorio=false)
	private String saida3;
	
	@Column(length=150)
	@CampoInfo(descricao="Horário de Entrada 4", obrigatorio=false)
	private String entrada4;
	
	@Column(length=150)
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
	@CampoInfo(descricao="Habilita Justificativa de Marcações Incorretas", obrigatorio=true)
	private Integer jus_marcacao_incorreta = 0;

	@Column
	@CampoInfo(descricao="Habilita Justificativa de Horas Extra", obrigatorio=true)
	private Integer jus_hora_credito = 0;

	@Column
	@CampoInfo(descricao="Habilita Justificativa de Compensação de Horas", obrigatorio=true)
	private Integer jus_hora_debito = 0;
	
	@Column
	@CampoInfo(descricao="Minutos Débito", obrigatorio=true)
	private Integer minutos_debito;

	@Column
	@CampoInfo(descricao="Minutos Crédito", obrigatorio=true)
	private Integer minutos_credito;
	
	@Column(length=150)
	@CampoInfo(descricao="Observação", obrigatorio=false)
	private String observacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDiasemana() {
		return diasemana;
	}

	public void setDiasemana(String diasemana) {
		this.diasemana = diasemana;
	}

	public String getJornada() {
		return jornada;
	}

	public void setJornada(String jornada) {
		this.jornada = jornada;
	}

	public String getLegenda() {
		return legenda;
	}

	public void setLegenda(String legenda) {
		this.legenda = legenda;
	}

	public String getEntrada1() {
		return entrada1;
	}

	public void setEntrada1(String entrada1) {
		this.entrada1 = entrada1;
	}

	public String getSaida1() {
		return saida1;
	}

	public void setSaida1(String saida1) {
		this.saida1 = saida1;
	}

	public String getSaida2() {
		return saida2;
	}

	public void setSaida2(String saida2) {
		this.saida2 = saida2;
	}

	public String getEntrada3() {
		return entrada3;
	}

	public void setEntrada3(String entrada3) {
		this.entrada3 = entrada3;
	}

	public String getSaida3() {
		return saida3;
	}

	public void setSaida3(String saida3) {
		this.saida3 = saida3;
	}

	public String getEntrada4() {
		return entrada4;
	}

	public void setEntrada4(String entrada4) {
		this.entrada4 = entrada4;
	}

	public String getSaida4() {
		return saida4;
	}

	public void setSaida4(String saida4) {
		this.saida4 = saida4;
	}

	public String getTrabalhadas_diurno() {
		return trabalhadas_diurno;
	}

	public void setTrabalhadas_diurno(String trabalhadas_diurno) {
		this.trabalhadas_diurno = trabalhadas_diurno;
	}

	public String getExtra_diurno() {
		return extra_diurno;
	}

	public void setExtra_diurno(String extra_diurno) {
		this.extra_diurno = extra_diurno;
	}

	public String getExtra_noturno() {
		return extra_noturno;
	}

	public void setExtra_noturno(String extra_noturno) {
		this.extra_noturno = extra_noturno;
	}

	public String getFaltas_diurno() {
		return faltas_diurno;
	}

	public void setFaltas_diurno(String faltas_diurno) {
		this.faltas_diurno = faltas_diurno;
	}

	public String getFaltas_noturno() {
		return faltas_noturno;
	}

	public void setFaltas_noturno(String faltas_noturno) {
		this.faltas_noturno = faltas_noturno;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getEntrada2() {
		return entrada2;
	}

	public void setEntrada2(String entrada2) {
		this.entrada2 = entrada2;
	}

	public String getTrabalhadas_noturno() {
		return trabalhadas_noturno;
	}

	public void setTrabalhadas_noturno(String trabalhadas_noturno) {
		this.trabalhadas_noturno = trabalhadas_noturno;
	}

	public Integer getJus_marcacao_incorreta() {
		return jus_marcacao_incorreta;
	}

	public void setJus_marcacao_incorreta(Integer jus_marcacao_incorreta) {
		this.jus_marcacao_incorreta = jus_marcacao_incorreta;
	}

	public Integer getJus_hora_credito() {
		return jus_hora_credito;
	}

	public void setJus_hora_credito(Integer jus_hora_credito) {
		this.jus_hora_credito = jus_hora_credito;
	}

	public Integer getJus_hora_debito() {
		return jus_hora_debito;
	}

	public void setJus_hora_debito(Integer jus_hora_debito) {
		this.jus_hora_debito = jus_hora_debito;
	}

	public Integer getMinutos_debito() {
		return minutos_debito;
	}

	public void setMinutos_debito(Integer minutos_debito) {
		this.minutos_debito = minutos_debito;
	}

	public Integer getMinutos_credito() {
		return minutos_credito;
	}

	public void setMinutos_credito(Integer minutos_credito) {
		this.minutos_credito = minutos_credito;
	}

}
