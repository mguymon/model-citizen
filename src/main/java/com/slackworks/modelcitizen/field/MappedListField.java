package com.slackworks.modelcitizen.field;

/**
 * A {@link MappedList} annotated Field in the {@link Blueprint} that is mapped 
 * to a {@link List} comprised of Models with a registered {@Blueprint}. 
 */
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

	/**
	 * The target Class with registered {@link Blueprint} that List is comprised of.
	 * If not set, defaults to annotated field's Class.s
	 */
	public Class getTarget() {
		return target;
	}


	/**
	 * Size of List to create
	 * 
	 * @return int
	 */
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
