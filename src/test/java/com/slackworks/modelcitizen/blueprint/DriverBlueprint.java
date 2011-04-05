package com.slackworks.modelcitizen.blueprint;

import com.slackworks.modelcitizen.annotation.Blueprint;
import com.slackworks.modelcitizen.annotation.Default;
import com.slackworks.modelcitizen.model.Driver;

@Blueprint(Driver.class )
public class DriverBlueprint {

		@Default
		public String name = "driver's name";
		
		@Default
		public Integer age= 16;
		
	}