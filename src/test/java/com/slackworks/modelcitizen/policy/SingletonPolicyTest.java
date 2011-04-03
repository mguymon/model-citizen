package com.slackworks.modelcitizen.policy;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.slackworks.modelcitizen.CreateModelException;
import com.slackworks.modelcitizen.ModelFactory;
import com.slackworks.modelcitizen.RegisterBlueprintException;
import com.slackworks.modelcitizen.blueprint.CarBlueprint;
import com.slackworks.modelcitizen.blueprint.DriverBlueprint;
import com.slackworks.modelcitizen.blueprint.UserBlueprint;
import com.slackworks.modelcitizen.blueprint.WheelBlueprint;
import com.slackworks.modelcitizen.model.Car;
import com.slackworks.modelcitizen.model.Driver;

public class SingletonPolicyTest {
	
	private ModelFactory modelFactory;
	private CarBlueprint carBlueprint = new CarBlueprint();
	private WheelBlueprint wheelBlueprint = new WheelBlueprint();
	private DriverBlueprint driverBlueprint = new DriverBlueprint();
	private UserBlueprint userBlueprint = new UserBlueprint();
	
	@Before
	public void setUp() throws RegisterBlueprintException {
		modelFactory = new ModelFactory();
		modelFactory.registerBlueprint( carBlueprint );
		modelFactory.registerBlueprint( wheelBlueprint );
		modelFactory.registerBlueprint( driverBlueprint );
		modelFactory.registerBlueprint( userBlueprint );
	}
	
	@Test
	public void SingletonPolicyWithClass() throws CreateModelException {
		modelFactory.addPolicy( new SingletonPolicy( Driver.class ) );
		
		Car car1 = modelFactory.createModel( Car.class );
		Car car2 = modelFactory.createModel( Car.class );
		Car car3 = modelFactory.createModel( Car.class );
		
		Driver driver = car1.getDriver();
		
		assertEquals( car2.getDriver(), driver );
		assertEquals( car3.getDriver(), driver );
	}
	
	@Test
	public void SingletonPolicyWithModel() throws CreateModelException {
		Driver driver = modelFactory.createModel( Driver.class );
		modelFactory.addPolicy( new SingletonPolicy( driver ) );
		
		Car car1 = modelFactory.createModel( Car.class );
		Car car2 = modelFactory.createModel( Car.class );
		Car car3 = modelFactory.createModel( Car.class );
		
		assertEquals( car1.getDriver(), driver );
		assertEquals( car2.getDriver(), driver );
		assertEquals( car3.getDriver(), driver );
	}
}
