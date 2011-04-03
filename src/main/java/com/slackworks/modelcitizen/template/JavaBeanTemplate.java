package com.slackworks.modelcitizen.template;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.reflect.MethodUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slackworks.modelcitizen.field.ModelField;

/**
 * Implementation of BlueprintTemplate using Reflection for a JavaBean Model
 */
public class JavaBeanTemplate implements BlueprintTemplate {

	private Logger logger = LoggerFactory.getLogger( this.getClass() );
	
	public <T> T set( T model, ModelField modelField ) throws BlueprintTemplateException {
		try {
			PropertyUtils.setProperty( model, modelField.getName(), modelField.getValue() );
		} catch (IllegalAccessException propertyException) {
			throw new BlueprintTemplateException( propertyException );
		} catch (InvocationTargetException propertyException) {
			throw new BlueprintTemplateException( propertyException );
		} catch (NoSuchMethodException propertyException) {
			throw new BlueprintTemplateException( propertyException );
		}
		
		return model;
	}

	public Object get(Object model, ModelField modelField) throws BlueprintTemplateException {
		try {
			return PropertyUtils.getProperty( model, modelField.getName() );
		} catch (IllegalAccessException propertyException) {
			throw new BlueprintTemplateException( propertyException );
		} catch (InvocationTargetException propertyException) {
			throw new BlueprintTemplateException( propertyException );
		} catch (NoSuchMethodException propertyException) {
			throw new BlueprintTemplateException( propertyException );
		} 
	}

}
