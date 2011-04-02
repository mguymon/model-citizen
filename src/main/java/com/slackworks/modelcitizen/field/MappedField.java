package com.slackworks.modelcitizen.field;

import java.lang.reflect.Field;

/**
 * A {@link Mapped} annotated Field in the {@link Blueprint} that is mapped 
 * to a registered {@Blueprint}
 */
public class MappedField implements ModelField {

	private String name;
	private Object value;
	private Class fieldClass;
	
	public Class getFieldClass() {
		return fieldClass;
	}

	public void setFieldClass(Class fieldClass) {
		this.fieldClass = fieldClass;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue( Object value ) {
		this.value = value;
	}
	
}
