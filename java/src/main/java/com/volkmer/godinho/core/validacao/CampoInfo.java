package com.volkmer.godinho.core.validacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CampoInfo {

	public String descricao();
	public boolean obrigatorio() default false;
	
}
