package com.slackworks.modelcitizen.blueprint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.slackworks.modelcitizen.annotation.Blueprint;
import com.slackworks.modelcitizen.annotation.Default;
import com.slackworks.modelcitizen.annotation.Mapped;
import com.slackworks.modelcitizen.annotation.MappedList;
import com.slackworks.modelcitizen.model.Car;
import com.slackworks.modelcitizen.model.Driver;
import com.slackworks.modelcitizen.model.Wheel;

@Blueprint( Car.class )
public class CarBlueprint {

	@Default
	public String make = "car make";

	@Default
	public String manufacturer = "car manufacturer";

	@Default
	public Integer milage = 100;

	@Default
	public Map status = new HashMap();

	@MappedList(target = Wheel.class, size = 4)
	public List<Wheel> wheels;

	@Mapped
	public Driver driver;

}