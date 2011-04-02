package com.slackworks.modelcitizen;

import java.util.List;

import com.slackworks.modelcitizen.field.ModelField;

/**
 * Erector for a Class to create an instance from a {@link Blueprint}
 */
public class Erector {

	private Blueprint blueprint;
	private List<ModelField> modelFields;
	private Class targetClass;
	
	public Blueprint getBlueprint() {
		return blueprint;
	}
	
	public void setBlueprint(Blueprint blueprint) {
		this.blueprint = blueprint;
	}
	
	public List<ModelField> getModelFields() {
		return modelFields;
	}
	
	public void setModelFields(List<ModelField> modelFields) {
		this.modelFields = modelFields;
	}
	
	public Class getTargetClass() {
		return targetClass;
	}
	
	public void setTargetClass(Class targetClass) {
		this.targetClass = targetClass;
	}
	
}
