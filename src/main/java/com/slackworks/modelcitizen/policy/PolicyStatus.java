package com.slackworks.modelcitizen.policy;

/**
 * Status of {@link Policy#process(ModelFactory, Blueprint, ModelField, Object)}
 * 
 */
public abstract class PolicyStatus {

	protected final String status;
	
	/**
	 * Default PolicyStatus, continue onto field evaluation.
	 */
	public final static PolicyStatus CONTINUE = new PolicyStatus( "continue" ) {};
	
	/**
	 * Return after executing Policies, skiping field evaluation.
	 */
	public final static PolicyStatus RETURN = new PolicyStatus( "return" ) {};
	
	public String getStatus() {
		return status;
	}
	
	protected PolicyStatus( String status ) {
		this.status = status;
	}
	
	
	
}
