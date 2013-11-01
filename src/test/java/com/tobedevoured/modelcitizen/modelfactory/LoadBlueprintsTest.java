package com.tobedevoured.modelcitizen.modelfactory;

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

import com.tobedevoured.modelcitizen.ModelFactory;
import com.tobedevoured.modelcitizen.RegisterBlueprintException;
import com.tobedevoured.modelcitizen.annotation.Default;
import com.tobedevoured.modelcitizen.blueprint.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoadBlueprintsTest {

    private ModelFactory modelFactory;
    private CarBlueprint carBlueprint = new CarBlueprint();
    private WheelBlueprint wheelBlueprint = new WheelBlueprint();
    private DriverBlueprint driverBlueprint = new DriverBlueprint();
    private UserBlueprint userBlueprint = new UserBlueprint();
    private OptionBlueprint optionBlueprint = new OptionBlueprint();

    @Before
    public void setUp() throws RegisterBlueprintException {
        modelFactory = new ModelFactory();
        modelFactory.registerBlueprint(carBlueprint);
        modelFactory.registerBlueprint(wheelBlueprint);
        modelFactory.registerBlueprint(driverBlueprint);
        modelFactory.registerBlueprint(userBlueprint);
        modelFactory.registerBlueprint(optionBlueprint);
    }

    @Test
    public void testRegisterBlueprintsByPackage() throws RegisterBlueprintException {
        modelFactory = new ModelFactory(); // reset modelFactory to set loading by package
        modelFactory.setRegisterBlueprintsByPackage("com.tobedevoured.modelcitizen");

        List<Class> blueprintClasses = new ArrayList<Class>();
        for (Object blueprint : modelFactory.getBlueprints()) {
            blueprintClasses.add(blueprint.getClass());
        }

        assertTrue("CarBlueprint should be registered", blueprintClasses.contains(CarBlueprint.class));
        assertTrue("WheelBlueprint should be registered", blueprintClasses.contains(WheelBlueprint.class));
        assertTrue("DriverBlueprint should be registered", blueprintClasses.contains(DriverBlueprint.class));
        assertTrue("UserBlueprint should be registered", blueprintClasses.contains(UserBlueprint.class));
    }

    @Test
    public void testRegisterBlueprint() throws RegisterBlueprintException {
        assertEquals(carBlueprint, modelFactory.getBlueprints().get(0));
        assertEquals(wheelBlueprint, modelFactory.getBlueprints().get(1));
        assertEquals(driverBlueprint, modelFactory.getBlueprints().get(2));
        assertEquals(userBlueprint, modelFactory.getBlueprints().get(3));
    }

    @Test(expected = RegisterBlueprintException.class)
    public void testSetRegisterBlueprintsWithException() throws RegisterBlueprintException {
        List blueprints = new ArrayList();
        blueprints.add(carBlueprint);
        blueprints.add(8);

        modelFactory.setRegisterBlueprints(blueprints);
    }

    @Test
    public void testDefaultFieldValue() throws NoSuchFieldException, IllegalAccessException {
        Field field = carBlueprint.getClass().getDeclaredField("mileage");
        Float val = (Float) field.get(carBlueprint);
        assertEquals(new Float(100.1), val);
    }
}
