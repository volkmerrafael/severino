package com.volkmer.godinho.severino.resource.mod_controleponto.notificacoes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.volkmer.godinho.core.email.Email;
import com.volkmer.godinho.core.util.enumeration.SimNao;
import com.volkmer.godinho.severino.entity.mod_controleponto.Ponto;
import com.volkmer.godinho.severino.entity.mod_geral.Configuracao;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.resource.mod_controleponto.ponto.PontoResource;
import com.volkmer.godinho.severino.resource.mod_controleponto.ponto.PontoStatus;
import com.volkmer.godinho.severino.resource.mod_geral.configuracao.ConfiguracaoResource;
import com.volkmer.godinho.severino.resource.mod_geral.usuario.UsuarioResource;

public class NotificaPorEmailUsuarioECoordenador {

	public void notificar(Integer ano, Integer mes, String userToken) throws Exception {
		
		try(UsuarioResource usuRes = new UsuarioResource()) {
			
			List<Usuario> listaDeCoordenadores = usuRes.listarCoordenadores(userToken);
			
			//Lista de coordenadores
			for (Usuario coordenador : listaDeCoordenadores) {

				List<Usuario> listaUsuarioDoCoordenador = new ArrayList<>();
				
				//Busca Lista de Usuários do Coordenador
				listaUsuarioDoCoordenador = usuRes.listarUsuariosDoCoordenador(coordenador.getAcesso().getToken());
				
				String mensagemFinalCoordenador = "";
						
				for (Usuario usu : listaUsuarioDoCoordenador) {
					
					if (usu.getUsuario_jira()!=null) {
					
						String emailFuncionario = "";
						
						String mensagemEmail = "Funcionário: " + usu.getNome()+"\n";
						String mensagemIncorreta = "";
						String mensagemCredito = "";
						String mensagemDebito = "";
						String mensagemTempoJira = "";
						
						try(PontoResource ponRes = new PontoResource()) {
							
							List<Ponto> listaPonto = ponRes.listarPontos(usu.getId(), coordenador.getAcesso().getToken(), ano, mes, null);
	
							for (Ponto ponto : listaPonto) {
								
								if (ponto.getStatus().equals(PontoStatus.MARCACAO_INCORRETA)) {
									mensagemIncorreta += " --> "+ponto.getData()+" - "+ponto.getObservacao()+".\n";
								} else {
								
									if (ponto.getStatus().equals(PontoStatus.CORRETO) || ponto.getStatus().equals(PontoStatus.CREDITO) || ponto.getStatus().equals(PontoStatus.DEBITO)) {
										
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
	
						}
						
						mensagemFinalCoordenador += mensagemEmail;
						emailFuncionario = mensagemEmail;
						
						if (mensagemIncorreta!=null && !mensagemIncorreta.equals("")) {
							mensagemIncorreta = "Marcação Incorreta:\n"+mensagemIncorreta;
							mensagemFinalCoordenador += mensagemIncorreta;
							emailFuncionario += mensagemIncorreta;
						}
						if (mensagemCredito!=null && !mensagemCredito.equals("")) {
							mensagemCredito = "Crédito:\n"+mensagemCredito;
							mensagemFinalCoordenador += mensagemCredito;
							emailFuncionario += mensagemCredito;
						}
						if (mensagemDebito!=null && !mensagemDebito.equals("")) {
							mensagemDebito = "Débito:\n"+mensagemDebito;
							mensagemFinalCoordenador += mensagemDebito;
							emailFuncionario += mensagemDebito;
						}
						if (mensagemTempoJira!=null && !mensagemTempoJira.equals("")) {
							mensagemTempoJira = "Tempo Jira:\n"+mensagemTempoJira;
							mensagemFinalCoordenador += mensagemTempoJira;
							emailFuncionario += mensagemTempoJira;
						}
		
						if (usu.getReceber_notificacao().equals(SimNao.SIM)) {
						
							if (emailFuncionario!=null && !emailFuncionario.equals("")) {
								
								String tituloEmail = "SEVERINO ("+LocalDateTime.now()+") - "+usu.getNome();
								
								Configuracao configuracao = new Configuracao();
								try(ConfiguracaoResource conRes = new ConfiguracaoResource()) {
									configuracao = conRes.busca(1);
								}
								
								//Envia e-mail para o usuário
								new Email().sendEmail(
											configuracao.getEmail_notificacao(), 
											configuracao.getEmail_senha(), 
											configuracao.getEmail_smtp_port(), 
											configuracao.getEmail_host(),
											tituloEmail, emailFuncionario, "rafael.volkmer1@gmail.com");//usu.getEmail()
							
							}
							
						}
						
					}
					
				}
				
				if (coordenador.getReceber_notificacao().equals(SimNao.SIM)) {
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
	
}
