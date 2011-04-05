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
	private Class target;
	
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

	public Class getTarget() {
		return target;
	}

	public void setTarget(Class target) {
		this.target = target;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fieldClass == null) ? 0 : fieldClass.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MappedField other = (MappedField) obj;
		if (fieldClass == null) {
			if (other.fieldClass != null)
				return false;
		} else if (!fieldClass.equals(other.fieldClass))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}
