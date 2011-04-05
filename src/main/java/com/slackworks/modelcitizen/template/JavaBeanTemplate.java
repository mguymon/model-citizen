package com.slackworks.modelcitizen.template;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.reflect.ConstructorUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slackworks.modelcitizen.field.ModelField;

/**
 * Implementation of BlueprintTemplate using Reflection for a JavaBean Model
 */
public class JavaBeanTemplate implements BlueprintTemplate {

	private Logger logger = LoggerFactory.getLogger( this.getClass() );
	
	public <T> T construct( Class<T> modelClass ) throws BlueprintTemplateException {
		try {
			return (T)ConstructorUtils.invokeConstructor( modelClass, null );
		} catch (NoSuchMethodException e) {
			throw new BlueprintTemplateException( e );
		} catch (IllegalAccessException e) {
			throw new BlueprintTemplateException( e );
		} catch (InvocationTargetException e) {
			throw new BlueprintTemplateException( e );
		} catch (InstantiationException e) {
			throw new BlueprintTemplateException( e );
		}
	}
	
	public <T> T set( T model, String property, Object value ) throws BlueprintTemplateException {
		try {
			PropertyUtils.setProperty( model, property, value );
		} catch (IllegalAccessException propertyException) {
			throw new BlueprintTemplateException( propertyException );
		} catch (InvocationTargetException propertyException) {
			throw new BlueprintTemplateException( propertyException );
		} catch (NoSuchMethodException propertyException) {
			throw new BlueprintTemplateException( propertyException );
		}
		
		return model;
	}
	
	public Object get( Object model, String property ) throws BlueprintTemplateException {
		try {
			return PropertyUtils.getProperty( model, property );
		} catch (IllegalAccessException propertyException) {
			throw new BlueprintTemplateException( propertyException );
		} catch (InvocationTargetException propertyException) {
			throw new BlueprintTemplateException( propertyException );
		} catch (NoSuchMethodException propertyException) {
			throw new BlueprintTemplateException( propertyException );
		} 
	}

}
