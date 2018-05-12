package com.volkmer.godinho.core;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import com.volkmer.godinho.severino.entity.Acesso;
import com.volkmer.godinho.severino.entity.Usuario;
import com.volkmer.godinho.severino.resource.acesso.AcessoResource;
import com.volkmer.godinho.severino.resource.acesso.AcessoTipo;
import com.volkmer.godinho.severino.resource.usuario.UsuarioResource;

@ApplicationPath("/rest")
public class App extends Application {
	
	@Context
	public static ServletContext context;
	
	private static Integer teste = 0;
	
	@Override
	public Set<Object> getSingletons() {
		
		teste ++;
		if (teste==1) {
			
			try (UsuarioResource usuRes = new UsuarioResource()) {
				
				@SuppressWarnings("resource")
				AcessoResource acessRes = new AcessoResource(usuRes);
				
				Usuario admin = usuRes.busca(1);
				if (admin==null) {
					
					Acesso acesso = new Acesso();
					acesso.setNomeacesso("admin");
					acesso.setSenha("admin");
					acesso.setTipo(AcessoTipo.ADMIN);
					
					admin = new Usuario();
					admin.setNome("Administrador");
					admin.setEmail("admin@casa.com");
					admin.setAcesso(acesso);
					
					usuRes.incluir(admin);
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		}
		
		return super.getSingletons();
	}
	
	public App() {
	}
	
}