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

import com.tobedevoured.modelcitizen.blueprint.CarBlueprint;
import com.tobedevoured.modelcitizen.field.DefaultField;
import com.tobedevoured.modelcitizen.field.ModelField;
import com.tobedevoured.modelcitizen.model.Car;
import com.tobedevoured.modelcitizen.template.BlueprintTemplateException;
import com.tobedevoured.modelcitizen.template.JavaBeanTemplate;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ErectorTest {

    public Erector erector;
    public DefaultField defaultField;
    public CarBlueprint carBlueprint = new CarBlueprint();

    @Before
    public void setUp() throws RegisterBlueprintException {
        defaultField = new DefaultField();
        defaultField.setName("milage");
        defaultField.setForce(false);
        defaultField.setValue(carBlueprint.mileage);
        defaultField.setTarget(Float.class);
        defaultField.setFieldClass(Float.class);

        erector = new Erector();
        erector.setTemplate(new JavaBeanTemplate());
        erector.setBlueprint(carBlueprint);
        erector.setModelFields(Arrays.<ModelField>asList(defaultField));
        erector.setTarget(Car.class);
        erector.setNewInstance(null);
    }

    @Test
    public void testGet() throws BlueprintTemplateException {
        Car car = new Car();
        car.setMileage(new Float(123.456));
        Float val = (Float) erector.getTemplate().get(car, "mileage");
        assertEquals(new Float(123.456), val);
    }
}
