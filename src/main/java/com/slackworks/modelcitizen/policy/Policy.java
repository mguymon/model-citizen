package com.slackworks.modelcitizen.policy;

import com.slackworks.modelcitizen.Erector;
import com.slackworks.modelcitizen.ModelFactory;
import com.slackworks.modelcitizen.field.ModelField;

/**
 * Policy parent, should implement child Policy
 */
public interface Policy {

	/**
	 * Class target of the Policy that has been mapped by a {@link Blueprint}
	 * 
	 * @return Class
	 */
	public Class getTarget();
	
}