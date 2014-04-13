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

import com.tobedevoured.modelcitizen.CreateModelException;
import com.tobedevoured.modelcitizen.ModelFactory;
import com.tobedevoured.modelcitizen.RegisterBlueprintException;
import com.tobedevoured.modelcitizen.blueprint.*;
import com.tobedevoured.modelcitizen.model.Car;
import com.tobedevoured.modelcitizen.model.Wheel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class ByReferenceTest {

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
    public void testCreateModelWithModel() throws RegisterBlueprintException, CreateModelException {

        Car car = modelFactory.createModel(new Car());
        assertEquals(carBlueprint.make, car.getMake());
        assertEquals(carBlueprint.manufacturer, car.getManufacturer());
        assertEquals(new Float(0.0), new Float(car.getMileage()));
        assertEquals(carBlueprint.status, car.getStatus());
        assertEquals(4, car.getWheels().size());

        for (Wheel wheel : car.getWheels()) {
            assertEquals(wheelBlueprint.size, wheel.getSize());
        }

        assertEquals(1, car.getSpares().size());
        for (Wheel wheel : car.getSpares()) {
            assertEquals(wheelBlueprint.size, wheel.getSize());
        }

        car.setMake("new make");
        car.setManufacturer("test manuf");

        car.setWheels(new ArrayList<Wheel>());
        Wheel wheel = new Wheel("mega tire");
        car.getWheels().add(wheel);

        car.setSpares(new HashSet<Wheel>());

        car = modelFactory.createModel(car);
        assertEquals("new make", car.getMake());
        assertEquals("test manuf", car.getManufacturer());
        assertEquals(0.0, car.getMileage(), 0);
        assertEquals(carBlueprint.status, car.getStatus());


        assertEquals("Car Wheels blueprint forced to the mapping of 4", 4, car.getWheels().size());
        assertEquals("The Car reference model should have 0 spares", 0, car.getSpares().size());
    }
}
