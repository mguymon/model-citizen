package com.slackworks.modelcitizen.policy;

import com.slackworks.modelcitizen.ModelFactoryException;

public class PolicyException extends ModelFactoryException {

	private static final long serialVersionUID = -215518503802560084L;

	public PolicyException( Throwable throwable ) {
		super( throwable );
	}
	
	public PolicyException( String message, Throwable throwable ) {
		super( message, throwable );
	}
	
	public PolicyException( String message ) {
		super( message );
	}
}
