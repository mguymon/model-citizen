package com.slackworks.modelcitizen.template;

import com.slackworks.modelcitizen.field.DefaultField;
import com.slackworks.modelcitizen.field.ModelField;

/**
 * BlueprintTemplate used to access the target Model
 */
public interface BlueprintTemplate {

	public <T> T set(T model, ModelField defaultField ) throws BlueprintTemplateException;
	public Object get(Object model, ModelField modelField ) throws BlueprintTemplateException;
}
