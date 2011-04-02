package com.slackworks.modelcitizen;

import com.slackworks.modelcitizen.template.BlueprintTemplate;

public interface Blueprint {

	public Class getTarget();
	
	public BlueprintTemplate getTemplate();
}
