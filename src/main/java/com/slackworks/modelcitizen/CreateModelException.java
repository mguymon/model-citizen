package com.slackworks.modelcitizen;

/**
 * Exception creating a Model
 */
public class CreateModelException extends Exception {
	private static final long serialVersionUID = 6729805576049811347L;

	public CreateModelException( String message ) {
		super( message );
	}
	
	public CreateModelException( Throwable throwable ) {
		super( throwable );
	}
}
