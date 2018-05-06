package com.volkmer.godinho.core.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.volkmer.godinho.core.App;

public class ListaDeClasses {

	private String packageName;
	
	public ListaDeClasses(String packageName) {
		this.packageName = packageName;
	}
	
	public List<Class<?>> getClasses() throws Exception {

		try {
			
			List<Class<?>> classes = new ArrayList<>();
			
			String path = this.packageName.replace(".", "/");
			String prefix = "/WEB-INF/classes/";
			
			@SuppressWarnings("unchecked")
			Set<String> resourcePaths = App.context.getResourcePaths(prefix+path);
			
			for (String arquivo : resourcePaths) {
				if (arquivo.lastIndexOf(".class")>=0) {
					
					int ini = arquivo.lastIndexOf("/")+1;
					int fim = arquivo.lastIndexOf(".");
					
					String nome = arquivo.substring(ini, fim);
					Class<?> forName = Class.forName(this.packageName+"."+nome);
					
					classes.add(forName);
					
				}
			}
			
			return classes;
			
		} catch (Exception e) {
			throw e;
		}

	}

}