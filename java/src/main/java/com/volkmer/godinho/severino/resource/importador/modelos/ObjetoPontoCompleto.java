package com.volkmer.godinho.severino.resource.importador.modelos
;
import com.volkmer.godinho.severino.entity.Ponto;

public class ObjetoPontoCompleto {
	
	private String funcionario;
	private String pis;
	private String data_admissao;
	private String funcao;
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
	public String getData_admissao() {
		return data_admissao;
	}
	public void setData_admissao(String data_admissao) {
		this.data_admissao = data_admissao;
	}
	public String getFuncao() {
		return funcao;
	}
	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
	public Ponto getPonto() {
		return ponto;
	}
	public void setPonto(Ponto ponto) {
		this.ponto = ponto;
	}
	
}