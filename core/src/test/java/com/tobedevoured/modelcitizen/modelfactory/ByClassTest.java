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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


import com.tobedevoured.modelcitizen.CreateModelException;
import com.tobedevoured.modelcitizen.Erector;
import com.tobedevoured.modelcitizen.ModelFactory;
import com.tobedevoured.modelcitizen.RegisterBlueprintException;
import com.tobedevoured.modelcitizen.blueprint.*;
import com.tobedevoured.modelcitizen.field.ModelField;
import com.tobedevoured.modelcitizen.template.BlueprintTemplateException;
import org.junit.Before;
import org.junit.Test;

import com.tobedevoured.modelcitizen.model.Car;
import com.tobedevoured.modelcitizen.model.Wheel;

public class ByClassTest {

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
    public void testCreateModelWithClass() throws RegisterBlueprintException, CreateModelException {

        Car car = modelFactory.createModel(Car.class);
        assertEquals(carBlueprint.make, car.getMake());
        assertEquals(carBlueprint.manufacturer, car.getManufacturer());
        assertEquals(carBlueprint.status, car.getStatus());
        assertEquals(4, car.getWheels().size());

        for (Wheel wheel : car.getWheels()) {
            assertEquals(wheelBlueprint.size, wheel.getSize());
        }
    }


    @Test
    public void testBlueprintWithPrimitive() throws CreateModelException, BlueprintTemplateException {
        Car car = modelFactory.createModel(Car.class);

        Erector erector = modelFactory.getErectors().get(Car.class);

        ModelField modelField = erector.getModelField("mileage");
        assertEquals(new Float(100.1), modelField.getValue());

        // Val is zero because primitive initializes as zero
        Object val = erector.getTemplate().get(new Car(), "mileage");
        assertEquals(new Float(0.0), val);

        // Val is zero because primitive initializes as zero
        assertEquals(0.0, car.getMileage(), 0);
    }
}
