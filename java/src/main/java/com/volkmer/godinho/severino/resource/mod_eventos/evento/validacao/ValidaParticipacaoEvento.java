package com.volkmer.godinho.severino.resource.mod_eventos.evento.validacao;

import javax.persistence.NoResultException;

import com.volkmer.godinho.core.rest.filters.RestException;
import com.volkmer.godinho.severino.entity.mod_eventos.Evento;
import com.volkmer.godinho.severino.entity.mod_eventos.ParticipanteEvento;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.resource.mod_eventos.evento.EventoResource;
import com.volkmer.godinho.severino.resource.mod_eventos.participanteevento.ParticipanteEventoResource;
import com.volkmer.godinho.severino.resource.mod_geral.usuario.UsuarioResource;

public class ValidaParticipacaoEvento {

	private RestException erroEventoNaoExiste = new RestException("Evento não Existe");
	private RestException erroUsuarioJaEstaConfirmadoNesteEvento = new RestException("Usuário Já Confirmado No Evento");

	public void validar(String token, Long idevento) throws Exception  {
		
		try (UsuarioResource usuRes = new UsuarioResource()) {
			
			Usuario usuario = usuRes.buscaUsuarioPeloToken(token);
			
			try (EventoResource eveRes = new EventoResource()) {
				Evento evento = eveRes.busca(idevento);
				
				try (ParticipanteEventoResource eveParRes = new ParticipanteEventoResource()) {
					ParticipanteEvento parEve = eveParRes.buscaParticipantePorEventoEUsuario(evento, usuario);
					if (parEve!=null) {
						throw erroUsuarioJaEstaConfirmadoNesteEvento;
					}
				}catch (NoResultException e) {
				}
			} catch (NoResultException e) {
				throw erroEventoNaoExiste;
			}
			
		} catch (NoResultException e) {
		}
	
	}
	
}
