package com.slackworks.modelcitizen.blueprint;

import java.util.ArrayList;

import com.slackworks.modelcitizen.annotation.Blueprint;
import com.slackworks.modelcitizen.annotation.Default;
import com.slackworks.modelcitizen.model.Wheel;

@Blueprint(Wheel.class)
public class WheelBlueprint {


	@Default
	public String name = "tire name";

	@Default
	public Integer size = 10;

	@Default
	public Object option = new ArrayList();

}