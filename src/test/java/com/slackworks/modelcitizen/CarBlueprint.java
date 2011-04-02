package com.slackworks.modelcitizen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.slackworks.modelcitizen.annotation.Default;
import com.slackworks.modelcitizen.annotation.Mapped;
import com.slackworks.modelcitizen.template.BlueprintTemplate;
import com.slackworks.modelcitizen.template.JavaBeanTemplate;

public class CarBlueprint implements Blueprint {

		public CarBlueprint() { 
			
		}
		
		@Default
		public String make = "car make";
		
		@Default
		public String manufacturer = "car manufacturer";
		
		@Default
		public Integer milage = 100;
		
		@Default
		public Map status = new HashMap();
		
		@Mapped
		public Wheel wheel;
		
		public Class getTarget() {
			return Car.class;
		}

		public BlueprintTemplate getTemplate() {
			return new JavaBeanTemplate();
		}
		
	}