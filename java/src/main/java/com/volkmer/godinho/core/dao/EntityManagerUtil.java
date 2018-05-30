package com.volkmer.godinho.core.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Table;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class EntityManagerUtil {

	private static SessionFactory sessions = null;
	
	static {
		
			Configuration cfg = new Configuration()
			    
			.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
			
			.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
			.setProperty("hibernate.connection.username", "postgres")
			.setProperty("hibernate.connection.password", "ids0207")
			.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5443/severino_0009")
			
			.setProperty("hibernate.c3p0.min_size", "5")
			.setProperty("hibernate.c3p0.max_size", "35")
			.setProperty("hibernate.c3p0.timeout", "1800")
			.setProperty("hibernate.c3p0.max_statements", "50")
			
			.setProperty("hibernate.hbm2ddl.auto", "update")
			.setProperty("hibernate.format_sql", "false")
			.setProperty("hibernate.show_sql", "false");
		
		try {
			List<Class<?>> classes = new ListaDeClasses("com.volkmer.godinho.severino.entity").getClasses();
			System.out.println("Inicio Classes");
			for (Class<?> classe : classes) {
				if (classe.isAnnotationPresent(Table.class)) {
					cfg.addAnnotatedClass(classe);
					System.out.println("--> " + classe.getSimpleName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sessions = cfg.buildSessionFactory();
		
	}

	public static EntityManager getEntityManager() {
		return sessions.createEntityManager();
	}
	
}
