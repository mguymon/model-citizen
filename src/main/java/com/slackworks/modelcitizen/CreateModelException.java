package com.slackworks.modelcitizen;

/**
 * Exception creating a Model
 */
public class CreateModelException extends ModelFactoryException {

	private static final long serialVersionUID = -421734984404486217L;

	public CreateModelException( String message ) {
		super( message );
	}
	
	public CreateModelException( Throwable throwable ) {
		super( throwable );
	}
}
