package com.slackworks.modelcitizen.policy;

public class PolicyException extends Exception {
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
