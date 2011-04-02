package com.slackworks.modelcitizen;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

public class ModelFactoryTest {

	private ModelFactory modelFactory;
	private Blueprint carBlueprint = new CarBlueprint();
	private Blueprint wheelBlueprint = new WheelBlueprint();
	
	@Before
	public void setUp() throws RegisterBlueprintException {
		modelFactory = new ModelFactory();
		modelFactory.registerBlueprint( carBlueprint );
		modelFactory.registerBlueprint( wheelBlueprint );
	}
	
	@Test
	public void testRegisterBlueprint() throws RegisterBlueprintException {
		assertEquals( carBlueprint, modelFactory.getBlueprints().get(0) );
		assertEquals( wheelBlueprint, modelFactory.getBlueprints().get(1) );
	}
	
	@Test
	public void testCreateModelWithModel() throws RegisterBlueprintException, CreateModelException {
		
		Car testModel = modelFactory.createModel( new Car() );
		assertEquals( "car make", testModel.getMake() );
		assertEquals( "car manufacturer", testModel.getManufacturer() );
		assertEquals( new Integer(100), testModel.getMilage() );
		assertEquals( new HashMap(), testModel.getStatus() );
		assertNotNull( testModel.getWheel() );
		
		testModel.setMake( "new make" );
		testModel.setManufacturer( "test manuf" );
		
		testModel = modelFactory.createModel( testModel );
		assertEquals( "new make", testModel.getMake() );
		assertEquals( "test manuf", testModel.getManufacturer() );
		assertEquals( new Integer(100), testModel.getMilage() );
		assertEquals( new HashMap(), testModel.getStatus() );
		assertNotNull( testModel.getWheel() );
		
	}
	
	@Test
	public void testCreateModelWithClass() throws RegisterBlueprintException, CreateModelException {
		
		Car testModel = modelFactory.createModel( Car.class );
		assertEquals( "car make", testModel.getMake() );
		assertEquals( "car manufacturer", testModel.getManufacturer() );
		assertEquals( new Integer(100), testModel.getMilage() );
		assertEquals( new HashMap(), testModel.getStatus() );
		assertNotNull( testModel.getWheel() );
	}
	
	@Test
	public void testCreateModelWithMappedField() throws RegisterBlueprintException, CreateModelException {
		Car car = new Car();
		Wheel wheel = new Wheel();
		wheel.setName( "wheel!" );
		car.setWheel( wheel );
		
		car = modelFactory.createModel( car );
		assertEquals( "car make", car.getMake() );
		assertEquals( "car manufacturer", car.getManufacturer() );
		assertEquals( new Integer(100), car.getMilage() );
		assertEquals( new HashMap(), car.getStatus() );
		
		assertEquals( wheel, car.getWheel() );
		assertEquals( new ArrayList(), wheel.getOption() );
		assertEquals( new Integer(10), wheel.getSize() );
	}
}
