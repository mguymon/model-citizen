package com.slackworks.modelcitizen;

import com.slackworks.modelcitizen.template.BlueprintTemplate;

/**
 * ModelFactory Blueprint
 * 
 */
public interface Blueprint {

	/**
	 * Target class Blueprint will create
	 * @return Class
	 */
	public Class getTarget();
	
	/**
	 * {@link BlueprintTemplate} used to access the Model
	 * @return
	 */
	public BlueprintTemplate getTemplate();
}
