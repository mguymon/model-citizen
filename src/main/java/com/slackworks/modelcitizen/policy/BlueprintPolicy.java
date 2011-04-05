package com.slackworks.modelcitizen.policy;

import java.util.Map;
import java.util.Set;

import com.slackworks.modelcitizen.Erector;
import com.slackworks.modelcitizen.ModelFactory;
import com.slackworks.modelcitizen.erector.Command;
import com.slackworks.modelcitizen.field.ModelField;

/**
 * Blueprint level Policy
 */
public interface BlueprintPolicy extends Policy {

	/**
	 * Process the model with the Policy.
	 * 
	 * @param modelFactory {@link ModelFactory modelFactory}
	 * @param erector {@link Erector}
	 * @param modelField {@link ModelField}
	 * @param model Object
	 * @return <M> model
	 * @throws PolicyException
	 */
	public Map<ModelField,Set<Command>> process(ModelFactory modelFactory, Erector erector, Object model) throws PolicyException;
	
}
