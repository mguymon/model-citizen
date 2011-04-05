package com.slackworks.modelcitizen.policy;

import com.slackworks.modelcitizen.Erector;
import com.slackworks.modelcitizen.ModelFactory;
import com.slackworks.modelcitizen.erector.Command;
import com.slackworks.modelcitizen.field.ModelField;

/**
 * Blueprint field level policy
 */
public interface FieldPolicy extends Policy {

	/**
	 * Process the model with the Policy.
	 * 
	 * @param modelFactory {@link ModelFactory}
	 * @param blueprint {@link Blueprint}
	 * @param modelField {@link ModelField}
	 * @param model Object
	 * @return <M> model
	 * @throws PolicyException
	 */
	public Command process(ModelFactory modelFactory, Erector erector, ModelField modelField, Object model) throws PolicyException;
	
}
