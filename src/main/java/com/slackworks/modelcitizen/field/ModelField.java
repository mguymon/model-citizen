package com.slackworks.modelcitizen.field;

/**
 * Annotated Field in a {@link Blueprint}
 */
public interface ModelField {
	
	/**
	 * {@link Blueprint} field name
	 * @return String
	 */
	public String getName();
	
	/**
	 * Set value of {@link Blueprint} field
	 * @return
	 */
	public void setValue( Object val );
	
	/**
	 * Value of {@link Blueprint} field
	 * @return
	 */
	public Object getValue();
	
	/**
	 * Target class for ModelField, defaults to FieldClass if not set.
	 * @return Class
	 */
	public Class getTarget();
	
	/**
	 * Class of annotated Field
	 * 
	 * @return Class
	 */
	public Class getFieldClass();
	
}
