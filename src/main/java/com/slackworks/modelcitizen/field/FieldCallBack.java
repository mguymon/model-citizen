package com.slackworks.modelcitizen.field;

import java.lang.reflect.ParameterizedType;

/**
 * Callback for a Field when the Model is created
 * 
 * @param <T> Field class
 */
public abstract class FieldCallBack<T> {
	private Class<T> fieldClass;
	

	/**
	 * Create new FieldCallBack instance
	 */
	public FieldCallBack() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		fieldClass = (Class<T>) genericSuperclass.getRawType();
	}
	
	/**
	 * Get the Field value
	 * 
	 * @param model Object
	 * @return <T> instance of Field class
	 */
	public abstract T get( Object referenceModel );
	
	
	/**
	 * FieldCallBack Field Class, <T>
	 * @return Class<T>
	 */
	public Class<T> getTarget() {
		return fieldClass;
	}
}
