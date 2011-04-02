package com.slackworks.modelcitizen;

import java.util.ArrayList;

import com.slackworks.modelcitizen.annotation.Default;
import com.slackworks.modelcitizen.annotation.Mapped;
import com.slackworks.modelcitizen.template.BlueprintTemplate;
import com.slackworks.modelcitizen.template.JavaBeanTemplate;

public class WheelBlueprint implements Blueprint {

		public WheelBlueprint() { 
			
		}
		
		@Default
		public String name = "tire name";
		
		@Default
		public Integer size = 10;
		
		@Default
		public Object option = new ArrayList();
		
		public Class getTarget() {
			return Wheel.class;
		}

		public BlueprintTemplate getTemplate() {
			return new JavaBeanTemplate();
		}
		
	}