package com.slackworks.modelcitizen;

public class ModelFactoryException extends Exception {
	
	private static final long serialVersionUID = -176482664001451382L;

	public ModelFactoryException( Throwable throwable ) {
		super( throwable );
	}
	
	public ModelFactoryException( String message, Throwable throwable ) {
		super( message, throwable );
	}
	
	public ModelFactoryException( String message ) {
		super( message );
	}
}
