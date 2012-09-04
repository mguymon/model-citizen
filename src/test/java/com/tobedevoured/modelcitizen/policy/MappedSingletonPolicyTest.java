package com.tobedevoured.modelcitizen.policy;

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

import org.junit.Before;
import org.junit.Test;

import com.tobedevoured.modelcitizen.ModelFactory;
import com.tobedevoured.modelcitizen.ModelFactoryException;
import com.tobedevoured.modelcitizen.RegisterBlueprintException;
import com.tobedevoured.modelcitizen.blueprint.CarBlueprint;
import com.tobedevoured.modelcitizen.blueprint.DriverBlueprint;
import com.tobedevoured.modelcitizen.blueprint.UserBlueprint;
import com.tobedevoured.modelcitizen.blueprint.WheelBlueprint;
import com.tobedevoured.modelcitizen.model.Car;
import com.tobedevoured.modelcitizen.model.Driver;

public class MappedSingletonPolicyTest {
	
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
	public void singletonPolicyWithClass() throws ModelFactoryException {
		modelFactory.addPolicy( new MappedSingletonPolicy( Driver.class ) );
		
		Car car1 = modelFactory.createModel( Car.class );
		Car car2 = modelFactory.createModel( Car.class );
		Car car3 = modelFactory.createModel( Car.class );
		
		Driver driver = car1.getDriver();
		
		assertEquals( car2.getDriver(), driver );
		assertEquals( car3.getDriver(), driver );
	}
	
	@Test
	public void singletonPolicyWithModel() throws ModelFactoryException {
		Driver driver = modelFactory.createModel( Driver.class );
		modelFactory.addPolicy( new MappedSingletonPolicy( driver ) );
		
		Car car1 = modelFactory.createModel( Car.class );
		Car car2 = modelFactory.createModel( Car.class );
		Car car3 = modelFactory.createModel( Car.class );
		
		assertEquals( car1.getDriver(), driver );
		assertEquals( car2.getDriver(), driver );
		assertEquals( car3.getDriver(), driver );
	}
}
