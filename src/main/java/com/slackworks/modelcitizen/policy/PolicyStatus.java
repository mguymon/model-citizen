package com.slackworks.modelcitizen.policy;

public abstract class PolicyStatus {

	protected final String status;
	
	public final static PolicyStatus CONTINUE = new PolicyStatus( "continue" ) {};
	public final static PolicyStatus RETURN = new PolicyStatus( "return" ) {};
	
	public String getStatus() {
		return status;
	}
	
	protected PolicyStatus( String status ) {
		this.status = status;
	}
	
	
	
}
