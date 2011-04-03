package com.slackworks.modelcitizen.field;

/**
 * Annotated Field in a {@link Blueprint}
 */
public interface ModelField {
	public String getName();
	public Object getValue();
	public Class getTarget();
	public Class getFieldClass();
}
