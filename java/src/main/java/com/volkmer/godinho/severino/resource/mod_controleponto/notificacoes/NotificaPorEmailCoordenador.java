package com.volkmer.godinho.severino.resource.mod_controleponto.notificacoes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.volkmer.godinho.core.email.Email;
import com.volkmer.godinho.severino.entity.mod_controleponto.Ponto;
import com.volkmer.godinho.severino.entity.mod_geral.Configuracao;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.resource.mod_acesso.acesso.AcessoResource;
import com.volkmer.godinho.severino.resource.mod_acesso.acesso.AcessoTipo;
import com.volkmer.godinho.severino.resource.mod_controleponto.ponto.PontoResource;
import com.volkmer.godinho.severino.resource.mod_controleponto.ponto.PontoStatus;
import com.volkmer.godinho.severino.resource.mod_geral.configuracao.ConfiguracaoResource;
import com.volkmer.godinho.severino.resource.mod_geral.usuario.UsuarioResource;

public class NotificaPorEmailCoordenador {

	public void notificar(Integer ano, Integer mes) throws Exception {
		
		List<Usuario> listaDeCoordenadores = new ArrayList<>();
		
		try(UsuarioResource usuRes = new UsuarioResource()) {
			List<Usuario> listaUsuario = usuRes.buscaTotos();
			for (Usuario usuario : listaUsuario) {
				if (usuario.getAcesso()!=null && usuario.getAcesso().getTipo().equals(AcessoTipo.COORDENADOR)) {
					try(AcessoResource aceRes = new AcessoResource()) {
						usuario.setAcesso(aceRes.buscaPorUsuario(usuario.getId()));
					}
					listaDeCoordenadores.add(usuario);
				}
			}
			
			//Lista de coordenadores
			for (Usuario coordenador : listaDeCoordenadores) {
				System.out.println("Coordenador "+coordenador.getNome());
				List<Usuario> listaUsuarioDoCoordenador = new ArrayList<>();
				
				//Busca Lista de Usuários do Coordenador
				listaUsuarioDoCoordenador = usuRes.listarUsuariosDoCoordenador(coordenador.getAcesso().getToken());
				
				String mensagemFinalCoordenador = "";
						
				for (Usuario usu : listaUsuarioDoCoordenador) {
					System.out.println("usuario "+usu.getNome());
					if (usu.getUsuario_jira()!=null) {
					
						String mensagemEmail = "Funcionário: " + usu.getNome()+"\n";
						String mensagemIncorreta = "";
						String mensagemCredito = "";
						String mensagemDebito = "";
						String mensagemTempoJira = "";
						
						try(PontoResource ponRes = new PontoResource()) {
							
							List<Ponto> listaPonto = ponRes.listarPontos(usu.getId(), coordenador.getAcesso().getToken(), ano, mes, null);
	
							for (Ponto ponto : listaPonto) {
								
								if (ponto.getStatus().equals(PontoStatus.MARCACAO_INCORRETA)) {
									mensagemIncorreta += " --> "+ponto.getData()+" - "+ponto.getData()+".\n";
								} else {
								
									if (ponto.getStatus().equals(PontoStatus.CREDITO)) {
										mensagemCredito += " --> "+ponto.getData()+" - "+ponto.getObservacao()+".\n";
									}
									
									if (ponto.getStatus().equals(PontoStatus.DEBITO)) {
										mensagemDebito += " --> "+ponto.getData()+" - "+ponto.getObservacao()+".\n";
									}
									
									//valorMinimo = Deve ter um campo no banco para cadastro
									BigDecimal valorMinimo = new BigDecimal(7.0);
									
									if (ponto.getJornada()!=null && ponto.getWorklog_diario().compareTo(valorMinimo)==-1 ) {
										mensagemEmail += " --> "+ponto.getData()+" - Lançado ("+ponto.getWorklog_diario()+") desejado ("+valorMinimo+").\n";
									}
								
								}
								
							}
	
						}
						
						mensagemFinalCoordenador += mensagemEmail;
					
						if (mensagemIncorreta!=null && !mensagemIncorreta.equals("")) {
							mensagemIncorreta = "Marcação Incorreta:\n"+mensagemIncorreta;
							mensagemFinalCoordenador += mensagemIncorreta;
						}
						if (mensagemCredito!=null && !mensagemCredito.equals("")) {
							mensagemCredito = "Crédito:\n"+mensagemCredito;
							mensagemFinalCoordenador += mensagemCredito;
						}
						if (mensagemDebito!=null && !mensagemDebito.equals("")) {
							mensagemDebito = "Débito:\n"+mensagemDebito;
							mensagemFinalCoordenador += mensagemDebito;
						}
						if (mensagemTempoJira!=null && !mensagemTempoJira.equals("")) {
							mensagemTempoJira = "Tempo Jira:\n"+mensagemTempoJira;
							mensagemFinalCoordenador += mensagemTempoJira;
						}
		
					}
					
				}
				
				if (mensagemFinalCoordenador!=null && !mensagemFinalCoordenador.equals("")) {
					
					String tituloEmail = "SEVERINO ("+LocalDateTime.now()+") - "+coordenador.getNome();
					
					Configuracao configuracao = new Configuracao();
					try(ConfiguracaoResource conRes = new ConfiguracaoResource()) {
						configuracao = conRes.busca(1);
					}
					
					//Envia e-mail para o coordenador
					new Email().sendEmail(
								configuracao.getEmail_notificacao(), 
								configuracao.getEmail_senha(), 
								configuracao.getEmail_smtp_port(), 
								configuracao.getEmail_host(),
								tituloEmail, mensagemFinalCoordenador, "rafael.volkmer1@gmail.com"); //coordenador.getEmail()
				
				}
				
			}
			
		}
		

		

		
	}
	
}
