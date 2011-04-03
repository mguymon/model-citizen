package com.slackworks.modelcitizen.policy;

import com.slackworks.modelcitizen.Blueprint;
import com.slackworks.modelcitizen.ModelFactory;
import com.slackworks.modelcitizen.field.ModelField;

public abstract class Policy {

	public abstract Class getTarget();
	
	public abstract <M> M process( ModelFactory modelFactory, Blueprint blueprint, ModelField modelField, M model ) throws PolicyException;
	
	public PolicyStatus getStatus() {
		return PolicyStatus.CONTINUE;
	}
}
