package com.tobedevoured.modelcitizen.callback;

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
import com.tobedevoured.modelcitizen.Erector;
import com.tobedevoured.modelcitizen.ModelFactory;
import com.tobedevoured.modelcitizen.RegisterBlueprintException;
import com.tobedevoured.modelcitizen.blueprint.CarBlueprint;
import com.tobedevoured.modelcitizen.blueprint.OptionBlueprint;
import com.tobedevoured.modelcitizen.blueprint.WheelBlueprint;
import com.tobedevoured.modelcitizen.model.Car;
import com.tobedevoured.modelcitizen.model.Wheel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class ConstructorCallbackTest {

    private ModelFactory modelFactory;
    private WheelBlueprint wheelBlueprint = new WheelBlueprint();
    private OptionBlueprint optionBlueprint = new OptionBlueprint();

    @Before
    public void setUp() throws RegisterBlueprintException {
        modelFactory = new ModelFactory();
        modelFactory.registerBlueprint(wheelBlueprint);
        modelFactory.registerBlueprint(optionBlueprint);
        modelFactory.registerBlueprint(CarBlueprint.class);
    }

    @Test
    public void testCreateModelWithConstructorCallBack() throws CreateModelException {

        Wheel wheel1 = modelFactory.createModel(Wheel.class);
        assertEquals("tire name", wheel1.getName());

        Wheel wheel2 = modelFactory.createModel(Wheel.class);
        assertEquals("tire name", wheel1.getName());

        assertFalse("Should create new instances", wheel1.equals(wheel2));
    }

    @Test
    public void testBlueprintConstructorOnlyTriggeredOncePerCreateModelInvocation() throws Exception {
        Erector erector = mock(Erector.class);
        modelFactory.getErectors().put(Car.class, erector);
        modelFactory.createModel(Car.class);
        verify(erector, times(1)).createNewInstance();
    }
}