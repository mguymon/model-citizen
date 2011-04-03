package com.slackworks.modelcitizen.policy;

import com.slackworks.modelcitizen.Blueprint;
import com.slackworks.modelcitizen.ModelFactory;
import com.slackworks.modelcitizen.field.ModelField;

/**
 * Policies for creating Models
 */
public abstract class Policy {

	/**
	 * Class target of the Policy that has been mapped by a {@link Blueprint}
	 * 
	 * @return Class
	 */
	public abstract Class getTarget();
	
	/**
	 * 
	 * @param modelFactory {@link ModelFactory}
	 * @param blueprint {@link Blueprint}
	 * @param modelField {@link ModelField}
	 * @param model Object
	 * @return <M>
	 * @throws PolicyException
	 */
	public abstract <M> M process( ModelFactory modelFactory, Blueprint blueprint, ModelField modelField, M model ) throws PolicyException;
	
	/**
	 * Status of {@link Policy#process(ModelFactory, Blueprint, ModelField, Object)}
	 * 
	 * @return {@link PolicyStatus}
	 */
	public PolicyStatus getStatus() {
		return PolicyStatus.CONTINUE;
	}
}
