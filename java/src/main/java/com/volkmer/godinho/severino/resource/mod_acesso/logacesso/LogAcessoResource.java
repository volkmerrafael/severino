package com.volkmer.godinho.severino.resource.mod_acesso.logacesso;

import java.time.LocalDateTime;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.mod_acesso.LogAcesso;

public class LogAcessoResource extends ResourceCRUD<LogAcesso> {
	
	public LogAcessoResource() {
	}
	
	public LogAcessoResource(ResourceCRUD<?> res) {
		super(res);
	}
	
	@Override
	public Class<LogAcesso> getModelClass() {
		return LogAcesso.class;
	}
	
	@Override
	protected void incluirPre(LogAcesso model) throws Exception {
		
		model.setData_hora(LocalDateTime.now());
		
	}
	
}
