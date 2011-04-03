package com.slackworks.modelcitizen.template;

import com.slackworks.modelcitizen.field.DefaultField;
import com.slackworks.modelcitizen.field.ModelField;

/**
 * BlueprintTemplate used to access the target Model
 */
public interface BlueprintTemplate {

	/**
	 * Set value of Model. Returns Model.
	 * 
	 * @param model T
	 * @param modelField {@link ModelField}
	 * @return T model
	 * @throws BlueprintTemplateException
	 */
	public <T> T set(T model, ModelField modelField ) throws BlueprintTemplateException;
	
	/**
	 * Get value of Model
	 * 
	 * @param model Object
	 * @param modelField {@link ModelField}
	 * @return Object get value
	 * @throws BlueprintTemplateException
	 */
	public Object get(Object model, ModelField modelField ) throws BlueprintTemplateException;
}
