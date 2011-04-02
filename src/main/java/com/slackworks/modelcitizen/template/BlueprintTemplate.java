package com.slackworks.modelcitizen.template;

import com.slackworks.modelcitizen.field.DefaultField;
import com.slackworks.modelcitizen.field.ModelField;

public interface BlueprintTemplate {

	public <T> T set(T model, ModelField defaultField ) throws TemplateException;
	public Object get(Object model, ModelField modelField ) throws TemplateException;
}
