package com.volkmer.godinho.severino.resource.importador.modelos
;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import com.volkmer.godinho.severino.entity.Ponto;

public class ObjetoPonto implements ObjetoPontoInterfaceImportacao {
	
	private Integer linha = 0;
	
	private static String funcionario;
	private static String pis;
	private static String data_admissao;
	private static String funcao;
	private static String departamento;
	private static String periodo;
	private String data;
	private String diasemana;
	private String jornada;
    private String legenda;
    private String entrada1;
    private String saida1;
    private String entrada2;
    private String saida2;
    private String entrada3;
    private String saida3;
    private String entrada4;
    private String saida4;
    private String trabalhadas_diurno;
    private String trabalhadas_noturno;
    private String extra_diurno;
    private String extra_noturno;
    private String faltas_diurno;
    private String faltas_noturno;
    private String observacao;

	public static String getFuncionario() {
		return funcionario;
	}

	public static void setFuncionario(String funcionario) {
		ObjetoPonto.funcionario = funcionario;
	}

	public static String getPis() {
		return pis;
	}

	public static void setPis(String pis) {
		ObjetoPonto.pis = pis;
	}

	public static String getData_admissao() {
		return data_admissao;
	}

	public static void setData_admissao(String data_admissao) {
		ObjetoPonto.data_admissao = data_admissao;
	}

	public static String getFuncao() {
		return funcao;
	}

	public static void setFuncao(String funcao) {
		ObjetoPonto.funcao = funcao;
	}

	public Integer getLinha() {
		return linha;
	}

	public void setLinha(Integer linha) {
		this.linha = linha;
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

	public String getEntrada2() {
		return entrada2;
	}

	public void setEntrada2(String entrada2) {
		this.entrada2 = entrada2;
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

	public String getTrabalhadas_noturno() {
		return trabalhadas_noturno;
	}

	public void setTrabalhadas_noturno(String trabalhadas_noturno) {
		this.trabalhadas_noturno = trabalhadas_noturno;
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

	public static String getDepartamento() {
		return departamento;
	}

	public static void setDepartamento(String departamento) {
		ObjetoPonto.departamento = departamento;
	}

	public static String getPeriodo() {
		return periodo;
	}

	public static void setPeriodo(String periodo) {
		ObjetoPonto.periodo = periodo;
	}

	@Override
	public void setValor(Integer pos, Integer linha, String newValor, Object oldObject) {
		
		this.linha = linha;
		
		newValor = newValor.trim();
		if (linha < 13) {
			if (pos.equals(0) && linha.equals(4) && newValor.indexOf("Funcionário:")!=-1) {
				this.funcionario = newValor.replace("Funcionário: ", "");
			} else 
			if (pos.equals(20) && linha.equals(4) && newValor.indexOf("PIS:")!=-1) {
				this.pis = newValor.replace("PIS: ", "");
			} else
			if (pos.equals(0) && linha.equals(6) && newValor.indexOf("Data Admissão:")!=-1) {
				this.data_admissao = newValor.replace("Data Admissão: ", "");
			} else
			if (pos.equals(7) && linha.equals(5) && newValor.indexOf("Função:")!=-1) {
				this.funcao = newValor.replace("Função: ", "");
			} else
			if (pos.equals(0) && linha.equals(5) && newValor.indexOf("Departamento:")!=-1) {
				this.departamento = newValor.replace("Departamento: ", "");
			} else 
			if (pos.equals(11) && linha.equals(0) && newValor.indexOf("Período de")!=-1) {
				this.periodo = newValor.replace("Período de ", "");
			}
			System.out.println("posicao: "+pos+" linha: "+linha+" newValor: "+newValor);
		} else
		if (linha >= 13) {
			//System.out.println("posicao: "+pos+" linha: "+linha+" newValor: "+newValor);
			switch (pos) {
			case 0:
				if (newValor.isEmpty()) {
					this.data = null;
				} else {
					this.data = newValor; //""+Float.valueOf(newValor).intValue();
				}
				break;
			case 1:
				this.diasemana = newValor;
				break;
			case 2:
				this.jornada = newValor;
				break;
			case 3:
				this.legenda = newValor;
				break;
			case 4:
				this.entrada1 = newValor;
				break;
			case 5:
				this.saida1 = newValor;
				break;
			case 6:
				this.entrada2 = newValor;
				break;
			case 7:
				this.saida2 = newValor;
				break;
			case 8:
				this.entrada3 = newValor;
				break;
			case 9:
				this.saida3 = newValor;
				break;
			case 10:
				this.entrada4 = newValor;
				break;
			case 11:
				this.saida4 = newValor;
				break;
			case 12:
				this.trabalhadas_diurno = newValor;
				break;
			case 13:
				this.trabalhadas_noturno = newValor;
				break;
			case 14:
				this.extra_diurno = newValor;
				break;
			case 15:
				this.extra_noturno = newValor;
				break;
			case 16:
				this.faltas_diurno = newValor;
				break;
			case 17:
				this.faltas_noturno = newValor;
				break;
			case 18:
				this.observacao = newValor;
				break;				
			default:
				break;
			}
		
		}
		
	}
	
	@Override
	public ObjetoPontoCompleto atualizar() {

		try {

			    ObjetoPontoCompleto objetoPontoCompleto = new ObjetoPontoCompleto();
				
			    objetoPontoCompleto.setFuncao(this.funcao);
			    objetoPontoCompleto.setData_admissao(this.data_admissao);
			    objetoPontoCompleto.setFuncionario(this.funcionario);
			    objetoPontoCompleto.setPis(this.pis);
			    objetoPontoCompleto.setDepartamento(this.departamento);
			    
				Ponto ponto = new Ponto();
				
				if (this.data!=null && !this.data.equals("")) {
					String dataCompleta = "";
					if (this.data.length()==4) {
						dataCompleta = this.data.substring(0, 3)+"0"+this.data.substring(3, 4);
					} else {
						dataCompleta = this.data;
					}
					dataCompleta += this.periodo.substring(5, 10);
					
					try {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						LocalDate date = LocalDate.parse(dataCompleta,formatter);
						ponto.setData(date);
					} catch (Exception e) {
						
					}
				}
				
				ponto.setDiasemana(this.diasemana);
				ponto.setJornada(this.jornada);
				ponto.setLegenda(this.legenda);
				ponto.setEntrada1(this.entrada1);
				ponto.setSaida1(this.saida1);
				ponto.setEntrada2(this.entrada2);
				ponto.setSaida2(this.saida2);
				ponto.setEntrada3(this.entrada3);
				ponto.setSaida3(this.saida3);
				ponto.setEntrada4(this.entrada4);
				ponto.setSaida4(this.saida4);
				ponto.setTrabalhadas_diurno(this.trabalhadas_diurno);
				ponto.setTrabalhadas_noturno(this.trabalhadas_noturno);
				ponto.setExtra_diurno(this.extra_diurno);
				ponto.setExtra_noturno(this.extra_noturno);
				ponto.setFaltas_diurno(this.faltas_diurno);
				ponto.setFaltas_noturno(this.faltas_noturno);
				ponto.setObservacao(this.observacao);
				
				objetoPontoCompleto.setPonto(ponto);
				
				return objetoPontoCompleto;
					
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public Boolean isFimArquivo() {
		Boolean fimArquivo = false;
		
		if (this.linha>=13 && ((this.data==null || this.data.equals("")) && (this.diasemana==null || this.diasemana.equals("")))) {
			fimArquivo = true;
		}
		return fimArquivo;
	}

	@Override
	public int getInicioArquivo() {
		return 0;
	}
		
}