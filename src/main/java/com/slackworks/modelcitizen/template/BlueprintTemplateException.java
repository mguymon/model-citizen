package com.slackworks.modelcitizen.template;

/**
 * Exception access Model with BlueprintTemplate
 */
public class BlueprintTemplateException extends Exception {
	public BlueprintTemplateException( Throwable throwable ) {
		super( throwable );
	}
	
	public BlueprintTemplateException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
