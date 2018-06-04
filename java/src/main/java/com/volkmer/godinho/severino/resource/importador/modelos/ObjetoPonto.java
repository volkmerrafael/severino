package com.volkmer.godinho.severino.resource.importador.modelos;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.volkmer.godinho.severino.entity.DiaSemana;
import com.volkmer.godinho.severino.entity.Jornada;
import com.volkmer.godinho.severino.entity.Legenda;
import com.volkmer.godinho.severino.entity.Ponto;

import lombok.Data;

@Data
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
			if (pos.equals(24) && linha.equals(4) && newValor.indexOf("PIS:")!=-1) {
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
			//System.out.println("posicao: "+pos+" linha: "+linha+" newValor: "+newValor);
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
			case 9:
				this.entrada3 = newValor;
				break;
			case 10:
				this.saida3 = newValor;
				break;
			case 12:
				this.entrada4 = newValor;
				break;
			case 13:
				this.saida4 = newValor;
				break;
			case 14:
				this.trabalhadas_diurno = newValor;
				break;
			case 15:
				this.trabalhadas_noturno = newValor;
				break;
			case 17:
				this.extra_diurno = newValor;
				break;
			case 18:
				this.extra_noturno = newValor;
				break;
			case 19:
				this.faltas_diurno = newValor;
				break;
			case 20:
				this.faltas_noturno = newValor;
				break;
			case 22:
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
			
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			    ObjetoPontoCompleto objetoPontoCompleto = new ObjetoPontoCompleto();
				
			    objetoPontoCompleto.setFuncao(this.funcao);
			    objetoPontoCompleto.setData_admissao(LocalDate.parse(this.data_admissao,formatter));
			    objetoPontoCompleto.setFuncionario(this.funcionario);
			    objetoPontoCompleto.setPis(Long.parseLong(this.pis));
			    objetoPontoCompleto.setDepartamento(this.departamento);
			    
			    //System.out.println(this.periodo);
			    
				//rever
				try {
					if (this.periodo!=null) {
						objetoPontoCompleto.setData_inicial_importacao(LocalDate.parse(this.periodo.substring(0, 10),formatter));
					}
				} catch (Exception e) {
				}
				//rever
				try {
					if (this.periodo!=null) {
						objetoPontoCompleto.setData_final_importacao(LocalDate.parse(this.periodo.substring(13, this.periodo.length()),formatter));
					}
				} catch (Exception e) {
				}
			
				
			    Ponto ponto = new Ponto();
				
				if (this.data!=null && !this.data.equals("")) {
					String dataCompleta = "";
					if (this.data.length()==4) {
						dataCompleta = this.data.substring(0, 3)+"0"+this.data.substring(3, 4);
					} else {
						dataCompleta = this.data;
					}
					dataCompleta += this.periodo.substring(5, 10);
					
					//rever
					try {
						ponto.setData(LocalDate.parse(dataCompleta,formatter));
					} catch (Exception e) {
					}
				}
				
				if (this.diasemana!=null) {
					DiaSemana diasem = new DiaSemana();
					diasem.setNome(this.diasemana);
					ponto.setDiasemana(diasem);
				}
				
				if (this.jornada!=null && !this.jornada.equals("")) {
					Jornada jd = new Jornada();
					jd.setPeriodo_jornada(this.jornada);
					
					String array[] = this.jornada.split(" ");
					
					ArrayList<LocalTime> listaLocalTime = new ArrayList<>();
					
					for (String string : array) {
						string = string+":00";
						LocalTime lt = LocalTime.parse(string);
						listaLocalTime.add(lt);						
					}
					
					LocalTime anterior = null;
					Integer jo = 0;
					Integer contador = 0;
					
					for (LocalTime l : listaLocalTime) {
						contador++;
						Duration duracao = null;
						
						if (contador!=1 && contador!=3) {
							LocalTime atual = l;
							duracao  = Duration.between(anterior, atual);
							jo += (int) duracao.toMinutes();
						} else {
							anterior = l;
						}
					}
					
					Integer horac = jo/60;
					Integer minutoc = jo%60;
					LocalTime lc = LocalTime.of(horac, minutoc, 0, 0);
					
					jd.setJornada(lc);
					ponto.setJornada(jd);
				}
				
				if (this.legenda!=null && !this.legenda.equals("")) {
					Legenda legenda = new Legenda();
					legenda.setSigla(this.legenda);
					ponto.setLegenda(legenda);
				}
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
