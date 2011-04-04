package com.slackworks.modelcitizen;

/**
 * Exception registering a Blueprint
 */
public class RegisterBlueprintException extends ModelFactoryException {
	
	private static final long serialVersionUID = -1245871142594594438L;

	public RegisterBlueprintException( String message ) {
		super( message );
	}
	
	public RegisterBlueprintException( Throwable throwable ) {
		super( throwable );
	}
	
	public RegisterBlueprintException( String message, Throwable throwable ) {
		super( message, throwable );
	}
}
