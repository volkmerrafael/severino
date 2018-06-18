package com.volkmer.godinho.severino.resource.importador.modelos;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.volkmer.godinho.severino.entity.AnoMes;
import com.volkmer.godinho.severino.entity.Departamento;
import com.volkmer.godinho.severino.entity.DiaSemana;
import com.volkmer.godinho.severino.entity.Empresa;
import com.volkmer.godinho.severino.entity.Funcao;
import com.volkmer.godinho.severino.entity.Importacao;
import com.volkmer.godinho.severino.entity.Jornada;
import com.volkmer.godinho.severino.entity.Legenda;
import com.volkmer.godinho.severino.entity.Ponto;
import com.volkmer.godinho.severino.entity.Usuario;
import com.volkmer.godinho.severino.resource.importador.ProcessaDadosPonto;

import lombok.Data;

@Data
public class ObjetoPonto implements ObjetoPontoInterfaceImportacao {
	
	private Integer linha = 0;
	
	private static String usuario_nome;
	private static String usuario_pis;
	private static String usuario_data_admissao;
	private static String nome_funcao;
	private static String nome_departamento;
	private static String importacao_periodo;
	private static String empresa_razao_social;
	private static String empresa_cnpj;
	private static String empresa_endereco;
	private static String empresa_cidade;
	private static String empresa_uf;
	
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
			if (pos.equals(0) && linha.equals(1) && newValor.indexOf("Empresa:")!=-1) {
				empresa_razao_social = newValor.replace("Empresa: ", "");
			} else
			if (pos.equals(16) && linha.equals(1) && newValor.indexOf("CNPJ:")!=-1) {
				empresa_cnpj = newValor.replace("CNPJ: ", "");
			} else
			if (pos.equals(0) && linha.equals(2) && newValor.indexOf("Endereço:")!=-1) {
				empresa_endereco = newValor.replace("Endereço: ", "");
			} else 
			if (pos.equals(7) && linha.equals(3) && newValor.indexOf("UF:")!=-1) {
				empresa_uf = newValor.replace("UF: ", "");
			} else 
			if (pos.equals(0) && linha.equals(3) && newValor.indexOf("Cidade:")!=-1) {
				empresa_cidade = newValor.replace("Cidade: ", "");
			} else					
			if (pos.equals(0) && linha.equals(4) && newValor.indexOf("Funcionário:")!=-1) {
				usuario_nome = newValor.replace("Funcionário: ", "");
			} else 
			if (pos.equals(20) && linha.equals(4) && newValor.indexOf("PIS:")!=-1) {
				usuario_pis = newValor.replace("PIS: ", "");
			} else 
			if (pos.equals(24) && linha.equals(4) && newValor.indexOf("PIS:")!=-1) {
				usuario_pis = newValor.replace("PIS: ", "");
			} else
			if (pos.equals(0) && linha.equals(6) && newValor.indexOf("Data Admissão:")!=-1) {
				usuario_data_admissao = newValor.replace("Data Admissão: ", "");
			} else
			if (pos.equals(7) && linha.equals(5) && newValor.indexOf("Função:")!=-1) {
				nome_funcao = newValor.replace("Função: ", "");
			} else
			if (pos.equals(0) && linha.equals(5) && newValor.indexOf("Departamento:")!=-1) {
				nome_departamento = newValor.replace("Departamento: ", "");
			} else 
			if (pos.equals(11) && linha.equals(0) && newValor.indexOf("Período de")!=-1) {
				importacao_periodo = newValor.replace("Período de ", "");
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
	public Ponto atualizar() {

		try {
			
			if (this.linha < 13 || (this.linha >= 13 && ((this.data==null || this.data.equals("") || this.data.indexOf("Saldo Banco de Horas")!=-1 || this.data.length()>6) && (this.diasemana==null || this.diasemana.equals(""))))) {
				
				Ponto ponto = new Ponto();
				return ponto;

			} else {
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				
				Ponto ponto = new Ponto();
				
				ponto.setUsuario(new Usuario());
				
				ponto.getUsuario().setFuncao(new Funcao());
				ponto.getUsuario().setDepartamento(new Departamento());
				ponto.getUsuario().setEmpresa(new Empresa());
	
				ponto.getUsuario().getFuncao().setNome(nome_funcao);
				ponto.getUsuario().getDepartamento().setNome(nome_departamento);
				
				ponto.getUsuario().setNome(usuario_nome);
				ponto.getUsuario().setData_admissao(LocalDate.parse(usuario_data_admissao,formatter));
				ponto.getUsuario().setPis(Long.parseLong(usuario_pis));
				
				ponto.getUsuario().getEmpresa().setCidade(empresa_cidade);
				ponto.getUsuario().getEmpresa().setRazao_social(empresa_razao_social);
				ponto.getUsuario().getEmpresa().setCnpj(empresa_cnpj);
				ponto.getUsuario().getEmpresa().setEndereco(empresa_endereco);
				ponto.getUsuario().getEmpresa().setUf(empresa_uf);
				
				if (importacao_periodo!=null) {
					if (importacao_periodo.length()==23) { //01/01/2017 01/01/2018
						ponto.setImportacao(new Importacao());
						ponto.getImportacao().setInicio_periodo(LocalDate.parse(importacao_periodo.substring(0, 10),formatter));
						ponto.getImportacao().setFinal_periodo(LocalDate.parse(importacao_periodo.substring(13, importacao_periodo.length()),formatter));
					}
				}
				
				if (this.data!=null && !this.data.equals("")) {
					this.processaData(ponto);					
				}
				
				if (this.diasemana!=null) {
					ponto.setDiasemana(new DiaSemana());
					ponto.getDiasemana().setNome(this.diasemana);
				}
				
				if (this.jornada!=null && !this.jornada.equals("")) {
					ponto.setJornada(new Jornada());
					this.processaJornada(ponto);
				}
				
				if (this.legenda!=null && !this.legenda.equals("")) {
					ponto.setLegenda(new Legenda());
					ponto.getLegenda().setSigla(this.legenda);
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
				
				new ProcessaDadosPonto().processar(ponto);
				
				return ponto;
			
			}
					
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	private void processaData(Ponto ponto) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		String dataCompleta = "";
		if (this.data.length()==4) {
			dataCompleta = this.data.substring(0, 3)+"0"+this.data.substring(3, 4);
		} else {
			dataCompleta = this.data;
		}
		dataCompleta += importacao_periodo.substring(5, 10);
		
		if (dataCompleta!=null && dataCompleta.length()==10) {
			ponto.setData(LocalDate.parse(dataCompleta,formatter));
			
			AnoMes anomes = new AnoMes();
			anomes.setAno(ponto.getData().getYear());
			anomes.setMes(ponto.getData().getMonth().getValue());
			
			ponto.setAnomes(anomes);
			
		}
		
	}

	private void processaJornada(Ponto ponto) {
		
		ponto.getJornada().setPeriodo_jornada(this.jornada);
		
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
		
		ponto.getJornada().setHoras(lc);
		
	}

	@Override
	public Boolean isFimArquivo() {
		Boolean fimArquivo = false;
		
		if (this.linha>=13 && ((this.data==null || this.data.equals("") || this.data.indexOf("Saldo Banco de Horas")!=-1 || this.data.length()>6) && (this.diasemana==null || this.diasemana.equals("")))) {
			fimArquivo = true;
		}
		return fimArquivo;
	}

	@Override
	public int getInicioArquivo() {
		return 0;
	}
		
}
