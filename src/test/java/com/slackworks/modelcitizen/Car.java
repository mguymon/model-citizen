package com.slackworks.modelcitizen;

import java.util.Map;

public class Car {
	private String make;
	private String manufacturer;
	private Integer milage;
	private Map status;
	private Wheel wheel;
	
	public Car() {
		
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Integer getMilage() {
		return milage;
	}

	public void setMilage(Integer milage) {
		this.milage = milage;
	}

	public Map getStatus() {
		return status;
	}

	public void setStatus(Map status) {
		this.status = status;
	}

	public Wheel getWheel() {
		return wheel;
	}

	public void setWheel(Wheel wheel) {
		this.wheel = wheel;
	}
	
	
}