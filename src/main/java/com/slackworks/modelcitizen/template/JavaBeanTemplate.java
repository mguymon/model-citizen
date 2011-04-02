package com.slackworks.modelcitizen.template;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.WordUtils;
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
			Method method = model.getClass().getMethod( "set" + WordUtils.capitalize( modelField.getName() ), new Class[] { modelField.getFieldClass() } );
			method.invoke( model, modelField.getValue() );
		} catch (SecurityException e) {
			throw new BlueprintTemplateException( e );
		} catch (NoSuchMethodException e) {
			throw new BlueprintTemplateException( e );
		} catch (IllegalArgumentException e) {
			throw new BlueprintTemplateException( e );
		} catch (IllegalAccessException e) {
			throw new BlueprintTemplateException( e );
		} catch (InvocationTargetException e) {
			throw new BlueprintTemplateException( e );
		}
		
		return model;
	}

	public Object get(Object model, ModelField modelField) throws BlueprintTemplateException {
		try {
			String methodSig = "get" + WordUtils.capitalize( modelField.getName() );
			Method method = model.getClass().getMethod( methodSig, null );
			return method.invoke( model );
		} catch (SecurityException e) {
			throw new BlueprintTemplateException( e );
		} catch (NoSuchMethodException e) {
			throw new BlueprintTemplateException( e );
		} catch (IllegalArgumentException e) {
			throw new BlueprintTemplateException( e );
		} catch (IllegalAccessException e) {
			throw new BlueprintTemplateException( e );
		} catch (InvocationTargetException e) {
			throw new BlueprintTemplateException( e );
		}
	}

}
