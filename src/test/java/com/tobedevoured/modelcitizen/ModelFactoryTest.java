package com.tobedevoured.modelcitizen;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
   *
 * http://www.apache.org/licenses/LICENSE-2.0
   *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


import com.tobedevoured.modelcitizen.blueprint.*;
import org.junit.Before;
import org.junit.Test;

import com.tobedevoured.modelcitizen.model.Car;
import com.tobedevoured.modelcitizen.model.Wheel;

public class ModelFactoryTest {

	private ModelFactory modelFactory;
	private CarBlueprint carBlueprint = new CarBlueprint();
	private WheelBlueprint wheelBlueprint = new WheelBlueprint();
	private DriverBlueprint driverBlueprint = new DriverBlueprint();
	private UserBlueprint userBlueprint = new UserBlueprint();
    private OptionBlueprint optionBlueprint = new OptionBlueprint();
	
	@Before
	public void setUp() throws RegisterBlueprintException {
		modelFactory = new ModelFactory();
		modelFactory.registerBlueprint( carBlueprint );
		modelFactory.registerBlueprint( wheelBlueprint );
		modelFactory.registerBlueprint( driverBlueprint );
		modelFactory.registerBlueprint( userBlueprint );
        modelFactory.registerBlueprint( optionBlueprint );
	}
	
	@Test 
	public void testRegisterBlueprintsByPackage() throws RegisterBlueprintException {
		modelFactory = new ModelFactory(); // reset modelFactory to set loading by package
		modelFactory.setRegisterBlueprintsByPackage( "com.tobedevoured.modelcitizen" );
				
		List<Class> blueprintClasses = new ArrayList<Class>();
		for ( Object blueprint : modelFactory.getBlueprints() ) {
			blueprintClasses.add( blueprint.getClass() );
		}
		
		assertTrue( "CarBlueprint should be registered", blueprintClasses.contains( CarBlueprint.class) );
		assertTrue( "WheelBlueprint should be registered", blueprintClasses.contains( WheelBlueprint.class) );
		assertTrue( "DriverBlueprint should be registered", blueprintClasses.contains( DriverBlueprint.class) );
		assertTrue( "UserBlueprint should be registered", blueprintClasses.contains( UserBlueprint.class) );
	}
	
	@Test
	public void testRegisterBlueprint() throws RegisterBlueprintException {
		assertEquals( carBlueprint, modelFactory.getBlueprints().get(0) );
		assertEquals( wheelBlueprint, modelFactory.getBlueprints().get(1) );
		assertEquals( driverBlueprint, modelFactory.getBlueprints().get(2) );
		assertEquals( userBlueprint, modelFactory.getBlueprints().get(3) );
	}
	
	@Test(expected=RegisterBlueprintException.class)
	public void testSetRegisterBlueprintsWithException() throws RegisterBlueprintException {
		List blueprints = new ArrayList();
		blueprints.add( carBlueprint );
		blueprints.add( new Integer(8) );
		
		modelFactory.setRegisterBlueprints( blueprints );
	}
	
	@Test
	public void testCreateModelWithModel() throws RegisterBlueprintException, CreateModelException {
		
		Car car = modelFactory.createModel( new Car() );
		assertEquals( carBlueprint.make, car.getMake() );
		assertEquals( carBlueprint.manufacturer, car.getManufacturer() );
		assertEquals( carBlueprint.milage, car.getMilage() );
		assertEquals( carBlueprint.status, car.getStatus() );
		assertEquals( 4, car.getWheels().size() );
		
		for ( Wheel wheel : car.getWheels() ) {
			assertEquals( wheelBlueprint.size, wheel.getSize() );
		}
		
		assertEquals( 1, car.getSpares().size() );
		for ( Wheel wheel : car.getSpares() ) {
			assertEquals( wheelBlueprint.size, wheel.getSize() );
		}
		
		car.setMake( "new make" );
		car.setManufacturer( "test manuf" );
		
		car.setWheels( new ArrayList<Wheel>() );
		Wheel wheel = new Wheel( "mega tire");
		car.getWheels().add(wheel);
		
		car.setSpares( new HashSet<Wheel>() );
		wheel = new Wheel( "spare tire");
		car.getSpares().add(wheel);
		
		car = modelFactory.createModel( car );
		assertEquals( "new make", car.getMake() );
		assertEquals( "test manuf", car.getManufacturer() );
		assertEquals( carBlueprint.milage, car.getMilage() );
		assertEquals( carBlueprint.status, car.getStatus() );
		
		assertEquals( 1, car.getWheels().size() );
		assertEquals( "tire name", car.getWheels().get(0).getName() );
		assertEquals( wheelBlueprint.size, car.getWheels().get(0).getSize() );
		
	}
	
	@Test
	public void testCreateModelWithClass() throws RegisterBlueprintException, CreateModelException {
		
		Car car = modelFactory.createModel( Car.class );
		assertEquals( carBlueprint.make, car.getMake() );
		assertEquals( carBlueprint.manufacturer, car.getManufacturer() );
		assertEquals( carBlueprint.milage, car.getMilage() );
		assertEquals( carBlueprint.status, car.getStatus() );
		assertEquals( 4, car.getWheels().size() );
		
		for ( Wheel wheel : car.getWheels() ) {
			assertEquals( wheelBlueprint.size, wheel.getSize() );
		}
	}
}
