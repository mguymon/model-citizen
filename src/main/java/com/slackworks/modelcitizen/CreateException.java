package com.slackworks.modelcitizen;

public class CreateException extends Exception {
	private static final long serialVersionUID = 6729805576049811347L;

	public CreateException( String message ) {
		super( message );
	}
	
	public CreateException( Throwable throwable ) {
		super( throwable );
	}
}
