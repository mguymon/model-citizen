package com.slackworks.modelcitizen.field;

/**
 * Callback for a Field when the Model is created
 * 
 * @param <T> Field class
 */
public abstract class FieldCallBack {
	
	/**
	 * Get the Field value
	 * 
	 * @param model Object
	 * @return <T> instance of Field class
	 */
	public abstract Object get( Object referenceModel );
}
