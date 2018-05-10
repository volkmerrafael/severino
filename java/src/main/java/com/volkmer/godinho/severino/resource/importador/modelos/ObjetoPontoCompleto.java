package com.volkmer.godinho.severino.resource.importador.modelos
;
import java.time.LocalDate;

import com.volkmer.godinho.severino.entity.Ponto;

public class ObjetoPontoCompleto {
	
	private String funcionario;
	private String pis;
	private LocalDate data_admissao;
	private String funcao;
	private String departamento;
	private Ponto ponto;
	
	public String getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}
	public String getPis() {
		return pis;
	}
	public void setPis(String pis) {
		this.pis = pis;
	}
	public LocalDate getData_admissao() {
		return data_admissao;
	}
	public void setData_admissao(LocalDate data_admissao) {
		this.data_admissao = data_admissao;
	}
	public String getFuncao() {
		return funcao;
	}
	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public Ponto getPonto() {
		return ponto;
	}
	public void setPonto(Ponto ponto) {
		this.ponto = ponto;
	}
	
}