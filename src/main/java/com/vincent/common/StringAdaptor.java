package com.vincent.common;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.vincent.common.convert.Convertor;

@Retention(RUNTIME)
@Target(ElementType.FIELD)
public @interface StringAdaptor {
	
	@SuppressWarnings("rawtypes")
	Class<? extends Convertor> value();

}