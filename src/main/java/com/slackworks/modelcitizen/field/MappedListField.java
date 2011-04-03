package com.slackworks.modelcitizen.field;

public class MappedListField implements ModelField {

	private String name;
	private Object value;
	private Class fieldClass;
	private Class target;
	private int size;
	
	public void setName( String name ) {
		this.name = name;
	}
	
	
	public String getName() {
		return name;
	}

	public void setValue( Object value ) {
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setTarget( Class target ) {
		this.target = target;
	}

	public Class getTarget() {
		return target;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public Class getFieldClass() {
		return fieldClass;
	}


	public void setFieldClass(Class fieldClass) {
		this.fieldClass = fieldClass;
	}

}
