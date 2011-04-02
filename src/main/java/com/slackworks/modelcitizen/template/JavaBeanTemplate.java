package com.slackworks.modelcitizen.template;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slackworks.modelcitizen.field.DefaultField;
import com.slackworks.modelcitizen.field.ModelField;

public class JavaBeanTemplate implements BlueprintTemplate {

	private Logger logger = LoggerFactory.getLogger( this.getClass() );
	
	public <T> T set( T model, ModelField modelField ) throws TemplateException {
		try {
			Method method = model.getClass().getMethod( "set" + WordUtils.capitalize( modelField.getName() ), new Class[] { modelField.getFieldClass() } );
			method.invoke( model, modelField.getValue() );
		} catch (SecurityException e) {
			throw new TemplateException( e );
		} catch (NoSuchMethodException e) {
			throw new TemplateException( e );
		} catch (IllegalArgumentException e) {
			throw new TemplateException( e );
		} catch (IllegalAccessException e) {
			throw new TemplateException( e );
		} catch (InvocationTargetException e) {
			throw new TemplateException( e );
		}
		
		return model;
	}

	public Object get(Object model, ModelField modelField) throws TemplateException {
		try {
			String methodSig = "get" + WordUtils.capitalize( modelField.getName() );
			Method method = model.getClass().getMethod( methodSig, null );
			return method.invoke( model );
		} catch (SecurityException e) {
			throw new TemplateException( e );
		} catch (NoSuchMethodException e) {
			throw new TemplateException( e );
		} catch (IllegalArgumentException e) {
			throw new TemplateException( e );
		} catch (IllegalAccessException e) {
			throw new TemplateException( e );
		} catch (InvocationTargetException e) {
			throw new TemplateException( e );
		}
	}

}
