package com.slackworks.modelcitizen;

import java.lang.reflect.ParameterizedType;

public abstract class FieldCallBack<T> {
	private Class<T> fieldClass;
	
	public abstract T get( Object model );
	
	public FieldCallBack() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		fieldClass = (Class<T>) genericSuperclass.getRawType();
	}
	
	public Class<T> target() {
		return fieldClass;
	}
}
