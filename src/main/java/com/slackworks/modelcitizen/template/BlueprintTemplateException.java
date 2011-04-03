package com.slackworks.modelcitizen.template;

import com.slackworks.modelcitizen.ModelFactoryException;

/**
 * Exception access Model with BlueprintTemplate
 */
public class BlueprintTemplateException extends ModelFactoryException {
	
	private static final long serialVersionUID = -3959792593614408374L;

	public BlueprintTemplateException( Throwable throwable ) {
		super( throwable );
	}
	
	public BlueprintTemplateException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
