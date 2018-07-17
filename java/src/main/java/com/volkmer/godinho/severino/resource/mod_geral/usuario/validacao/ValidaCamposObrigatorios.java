package com.volkmer.godinho.severino.resource.mod_geral.usuario.validacao;

import com.volkmer.godinho.core.rest.filters.RestException;
import com.volkmer.godinho.core.util.AcaoTipo;
import com.volkmer.godinho.core.util.SimNao;
import com.volkmer.godinho.core.util.ValidaCPFeCNPJ;
import com.volkmer.godinho.core.util.ValidaEmail;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.resource.mod_acesso.acesso.AcessoTipo;

public class ValidaCamposObrigatorios {

	private RestException campoObrigatorio = new RestException("Campo Obrigatório");
	private RestException campoInvalido = new RestException("Campo Inválido");

	public void validar(Usuario usuario, AcaoTipo acaotipo) throws RestException  {
		
		if (acaotipo.equals(AcaoTipo.ALTERACAO) && usuario.getId()==null) {
			throw campoObrigatorio.addDetalhe("Em alteração o campo código é Obrigatório");
		}
		
		if (usuario.getAcesso()==null || usuario.getAcesso().getTipo()==null) {
			throw campoObrigatorio.addDetalhe("Campos de Acesso são Obrigatórios em Alteração");
		}
		
		if (usuario.getNome()=="") {
			throw campoObrigatorio.addDetalhe("Nome");
		}
		
		if (usuario.getAcesso().getTipo().equals(AcessoTipo.NORMAL) || usuario.getAcesso().getTipo().equals(AcessoTipo.COORDENADOR)) {
			if (usuario.getData_admissao()==null) {
				throw campoObrigatorio.addDetalhe("Data de Admissão");
			}
			if (usuario.getReceber_notificacao()!=null && usuario.getReceber_notificacao().equals(SimNao.SIM)) {
				if (usuario.getEmail()==null || usuario.getEmail().equals("")) {
					throw campoObrigatorio.addDetalhe("E-mail");
				} else if (!new ValidaEmail().validaEmail(usuario.getEmail())){
					throw campoInvalido.addDetalhe("E-mail");
				}
			}
			if (usuario.getDepartamento()==null || usuario.getDepartamento().getId()==null) {
				throw campoObrigatorio.addDetalhe("Departamento");
			}
			if (usuario.getFuncao()==null || usuario.getFuncao().getId()==null) {
				throw campoObrigatorio.addDetalhe("Função");
			}
			if (usuario.getEmpresa()==null || usuario.getEmpresa().getId()==null) {
				throw campoObrigatorio.addDetalhe("Empresa");
			}
			if (usuario.getNome()=="") {
				throw campoObrigatorio.addDetalhe("Nome");
			}
			if (usuario.getPis()==null) {
				throw campoObrigatorio.addDetalhe("P.I.S.");
			}
			
			if (usuario.getCpf()!=null && !usuario.getCpf().equals("")) {
				if (!new ValidaCPFeCNPJ().isValidCPF(usuario.getCpf())) {
					throw campoInvalido.addDetalhe("C.P.F.");
				}
			}
		}

	}
	
}
