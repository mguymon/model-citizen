package com.tobedevoured.modelcitizen.annotation;

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
import static org.junit.Assert.assertEquals;

import com.tobedevoured.modelcitizen.blueprint.*;
import com.tobedevoured.modelcitizen.model.Driver;
import org.junit.Before;
import org.junit.Test;

import com.tobedevoured.modelcitizen.CreateModelException;
import com.tobedevoured.modelcitizen.ModelFactory;
import com.tobedevoured.modelcitizen.RegisterBlueprintException;
import com.tobedevoured.modelcitizen.model.Car;

public class ForceTest {

    private ModelFactory modelFactory;
    private DriverBlueprint driverBlueprint = new DriverBlueprint();

    @Before
    public void setUp() throws RegisterBlueprintException {
        modelFactory = new ModelFactory();
        modelFactory.registerBlueprint( driverBlueprint );
    }

    @Test
    public void testDriverNameIsForced() throws CreateModelException {
        Driver driver = new Driver();
        driver.setName( "Test Name");
        driver = modelFactory.createModel( Driver.class );

        assertEquals( "Driver name is a forced default", "Lev the Driver", driver.getName() );
    }
}
