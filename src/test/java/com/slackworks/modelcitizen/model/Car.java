package com.slackworks.modelcitizen.model;

import java.util.List;
import java.util.Map;


public class Car {
	private String make;
	private String manufacturer;
	private Integer milage;
	private Map status;
	private List<Wheel> wheels;
	private Driver driver;
	
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

	public List<Wheel> getWheels() {
		return wheels;
	}

	public void setWheels(List<Wheel> wheels) {
		this.wheels = wheels;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	
}