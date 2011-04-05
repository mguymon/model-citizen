package com.slackworks.modelcitizen.template;

import com.slackworks.modelcitizen.field.DefaultField;
import com.slackworks.modelcitizen.field.ModelField;

/**
 * BlueprintTemplate used to access the target Model
 */
public interface BlueprintTemplate {

	public <T> T construct( Class<T> modelClass ) throws BlueprintTemplateException;
	
	/**
	 * Set value of Model. Returns Model.
	 * 
	 * @param model T
	 * @param String property
	 * @params Object value
	 * @return T model
	 * @throws BlueprintTemplateException
	 */
	public <T> T set(T model, String property, Object value ) throws BlueprintTemplateException;
	
	/**
	 * Get value of Model
	 * 
	 * @param model Object
	 * @param String property
	 * @return Object get value
	 * @throws BlueprintTemplateException
	 */
	public Object get(Object model, String property ) throws BlueprintTemplateException;
}
