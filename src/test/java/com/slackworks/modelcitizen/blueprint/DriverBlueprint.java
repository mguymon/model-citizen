package com.slackworks.modelcitizen.blueprint;

import java.util.ArrayList;


import com.slackworks.modelcitizen.Blueprint;
import com.slackworks.modelcitizen.annotation.Default;
import com.slackworks.modelcitizen.annotation.Mapped;
import com.slackworks.modelcitizen.model.Driver;
import com.slackworks.modelcitizen.template.BlueprintTemplate;
import com.slackworks.modelcitizen.template.JavaBeanTemplate;

public class DriverBlueprint implements Blueprint {

		
		@Default
		public String name = "driver's name";
		
		@Default
		public Integer age= 16;
		
		public Class getTarget() {
			return Driver.class;
		}

		public BlueprintTemplate getTemplate() {
			return new JavaBeanTemplate();
		}
		
	}