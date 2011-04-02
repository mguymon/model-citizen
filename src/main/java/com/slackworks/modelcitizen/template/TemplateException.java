package com.slackworks.modelcitizen.template;

public class TemplateException extends Exception {
	public TemplateException( Throwable throwable ) {
		super( throwable );
	}
	
	public TemplateException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
