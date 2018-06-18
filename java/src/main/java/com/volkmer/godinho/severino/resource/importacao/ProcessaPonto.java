package com.volkmer.godinho.severino.resource.importacao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.volkmer.godinho.severino.entity.Acesso;
import com.volkmer.godinho.severino.entity.AnoMes;
import com.volkmer.godinho.severino.entity.DiaSemana;
import com.volkmer.godinho.severino.entity.Importacao;
import com.volkmer.godinho.severino.entity.Jornada;
import com.volkmer.godinho.severino.entity.Legenda;
import com.volkmer.godinho.severino.entity.Ponto;
import com.volkmer.godinho.severino.entity.Usuario;
import com.volkmer.godinho.severino.resource.anomes.AnoMesResource;
import com.volkmer.godinho.severino.resource.diasemana.DiaSemanaResource;
import com.volkmer.godinho.severino.resource.jornada.JornadaResource;
import com.volkmer.godinho.severino.resource.legenda.LegendaResource;
import com.volkmer.godinho.severino.resource.ponto.PontoResource;
import com.volkmer.godinho.severino.resource.usuario.UsuarioResource;

public class ProcessaPonto {

	public void processar(List<Ponto> listaObjPonto, Importacao importacao) throws Exception {
		
		List<Usuario> listaUsuarioBanco = new ArrayList<Usuario>();
		Map<Long, Usuario> mapaUsuario = new HashMap<Long, Usuario>();
		try (UsuarioResource usuarioRes = new UsuarioResource()) {
			listaUsuarioBanco = usuarioRes.buscaTotos();
			for (Usuario usuario : listaUsuarioBanco) {
				mapaUsuario.put(usuario.getPis(), usuario); 
			}
		}
		
		List<DiaSemana> listaDiaSemanaBanco = new ArrayList<DiaSemana>();
		Map<String, DiaSemana> mapaDiaSemana = new HashMap<String, DiaSemana>();
		try (DiaSemanaResource diaSemanaRes = new DiaSemanaResource()) {
			listaDiaSemanaBanco = diaSemanaRes.buscaTotos();
			for (DiaSemana diaSemana : listaDiaSemanaBanco) {
				mapaDiaSemana.put(diaSemana.getNome(), diaSemana); 
			}
		}
		
		List<Jornada> listaJornadaBanco = new ArrayList<Jornada>();
		Map<String, Jornada> mapaJornada = new HashMap<String, Jornada>();
		try (JornadaResource jornadaRes = new JornadaResource()) {
			listaJornadaBanco = jornadaRes.buscaTotos();
			for (Jornada jornada : listaJornadaBanco) {
				mapaJornada.put(jornada.getPeriodo_jornada(), jornada); 
			}
		}
		
		List<Legenda> listaLegendaBanco = new ArrayList<Legenda>();
		Map<String, Legenda> mapaLegenda = new HashMap<String, Legenda>();
		try (LegendaResource legendaRes = new LegendaResource()) {
			listaLegendaBanco = legendaRes.buscaTotos();
			for (Legenda legenda : listaLegendaBanco) {
				mapaLegenda.put(legenda.getSigla(), legenda); 
			}
		}
		
		List<AnoMes> listaAnoMesBanco = new ArrayList<AnoMes>();
		Map<String, AnoMes> mapaAnoMes = new HashMap<String, AnoMes>();
		try (AnoMesResource anomesRes = new AnoMesResource()) {
			listaAnoMesBanco = anomesRes.buscaTotos();
			for (AnoMes anoMes : listaAnoMesBanco) {
				mapaAnoMes.put(anoMes.getAno()+""+anoMes.getMes(), anoMes);
			}
		}
		
		//Usuario usuarioBanco = new Usuario();
		Ponto pontoBanco = new Ponto();
		
		Long pisEmProcessamento = null;
		Integer contadorUsuarios = 0;
		Integer contador = 0;
		
		List<Ponto> listaPontoBanco = new ArrayList<Ponto>();
		Map<String, Ponto> mapaPonto = new HashMap<String, Ponto>();
		
		for (Ponto ponto : listaObjPonto) {
			
			importacao.setInicio_periodo(ponto.getImportacao().getInicio_periodo());
			importacao.setFinal_periodo(ponto.getImportacao().getFinal_periodo());
			importacao.setArquivoimportacao(null);
			
			//Atualiza informação de quantos usuário foram importado
			if (ponto.getUsuario()!=null && !ponto.getUsuario().getPis().equals(pisEmProcessamento)) {
				
				pisEmProcessamento = ponto.getUsuario().getPis();	
				
				importacao.setQuantidade_usuario(++contadorUsuarios);
				
				try (ImportacaoResource impRes = new ImportacaoResource()) {
					impRes.alterar(importacao);
				}
				
			}
			
			//Primeiro Registro irá pegar o periodo que esta vindo do arquivo e consultar todos os pontos do banco e mapear
			if (contador==0) {
				++contador;
				//Map de todos os pontos já no banco deste periodo
				try (PontoResource pontoRes = new PontoResource()) {
					
					listaPontoBanco = pontoRes.buscaPorPeriodo(importacao.getInicio_periodo(),importacao.getFinal_periodo());
					
					for (Ponto pontoConsultaBanco : listaPontoBanco) {
						mapaPonto.put(pontoConsultaBanco.getData()+""+pontoConsultaBanco.getUsuario().getPis(), pontoConsultaBanco); 
					}
					
				}
				
			}
			
			pontoBanco = mapaPonto.get(ponto.getData()+""+ponto.getUsuario().getPis());
			
			if (pontoBanco==null) {
				//Incluir Ponto
				ponto.setImportacao(importacao);
				
				Usuario usuarioBanco = mapaUsuario.get(ponto.getUsuario().getPis());
				
				if (usuarioBanco==null) {
					
					try (UsuarioResource usuarioRes = new UsuarioResource()) {
						usuarioBanco = new ProcessaDependenciasUsuario().buscaOuCriaDependenciaUsuario(ponto.getUsuario());
						
						Acesso acesso = new Acesso();
						acesso.setNomeacesso(ponto.getUsuario().getPis().toString());
						acesso.setSenha(ponto.getUsuario().getPis().toString());
						usuarioBanco.setAcesso(acesso);
						
						usuarioRes.incluir(usuarioBanco);
						mapaUsuario.put(ponto.getUsuario().getPis(), usuarioBanco);
					}

					
				}
				
				ponto.setUsuario(usuarioBanco);
				
				if (ponto.getLegenda()!=null && ponto.getLegenda().getSigla()!="") {						
					ponto.setLegenda(new ProcessaLegenda().processar(mapaLegenda, ponto.getLegenda()));
				}
						
				//buscar Jornada 
				if (ponto.getJornada()!=null && ponto.getJornada().getPeriodo_jornada()!=null) {
					ponto.setJornada(new ProcessaJornada().processar(mapaJornada, ponto.getJornada()));
				}
				
				//buscar DiaSemana
				ponto.setDiasemana(new ProcessaDiaSemana().processar(mapaDiaSemana, ponto.getDiasemana()));
				//buscar AnoMes
				ponto.setAnomes(new ProcessaAnoMes().processar(mapaAnoMes, ponto.getAnomes()));
				
				try (PontoResource pontoRes = new PontoResource()) {
					pontoRes.incluir(ponto);
				}
				
			} else {
				
				//Alterar Ponto
				if (ponto.getLegenda()!=null && ponto.getLegenda().getSigla()!="") {						
					pontoBanco.setLegenda(new ProcessaLegenda().processar(mapaLegenda, ponto.getLegenda()));
				} else {
					pontoBanco.setLegenda(null);
				}
						
				//buscar Jornada 
				if (ponto.getJornada()!=null && ponto.getJornada().getPeriodo_jornada()!=null) {
					pontoBanco.setJornada(new ProcessaJornada().processar(mapaJornada, ponto.getJornada()));
				} else {
					pontoBanco.setLegenda(null);
				}
				
				pontoBanco.setEntrada1(ponto.getEntrada1());
				pontoBanco.setEntrada2(ponto.getEntrada2());
				pontoBanco.setEntrada3(ponto.getEntrada3());
				pontoBanco.setEntrada4(ponto.getEntrada4());
				
				pontoBanco.setSaida1(ponto.getSaida1());
				pontoBanco.setSaida2(ponto.getSaida2());
				pontoBanco.setSaida3(ponto.getSaida3());
				pontoBanco.setSaida4(ponto.getSaida4());
				
				pontoBanco.setMinutos_abono(ponto.getMinutos_abono());
				pontoBanco.setMinutos_credito(ponto.getMinutos_credito());
				pontoBanco.setMinutos_debito(ponto.getMinutos_debito());
				pontoBanco.setMinutos_trabalhados(ponto.getMinutos_trabalhados());
				
				pontoBanco.setObservacao(ponto.getObservacao());
				pontoBanco.setStatus(ponto.getStatus());
				
				pontoBanco.setExtra_diurno(ponto.getExtra_diurno());
				pontoBanco.setExtra_noturno(ponto.getExtra_noturno());
				pontoBanco.setFaltas_diurno(ponto.getFaltas_noturno());
				pontoBanco.setFaltas_noturno(ponto.getFaltas_noturno());
				pontoBanco.setTrabalhadas_diurno(ponto.getTrabalhadas_diurno());
				pontoBanco.setTrabalhadas_noturno(ponto.getTrabalhadas_noturno());
					
				try (PontoResource pontoRes = new PontoResource()) {
					pontoRes.alterar(pontoBanco);	
				}
				
			}
			
		}
			
	}
	
}
