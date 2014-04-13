package com.tobedevoured.modelcitizen.spring;

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
import com.tobedevoured.modelcitizen.RegisterBlueprintException;
import com.tobedevoured.modelcitizen.blueprint.*;

import com.tobedevoured.modelcitizen.model.SportsCar;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/testContext.xml"})
public class ModelFactoryBeanTest {

    @Autowired
    ModelFactoryBean modelFactory;

    @Test
    public void testRegisterBlueprintsByPackage() throws RegisterBlueprintException {
        List<Class> blueprintClasses = new ArrayList<Class>();
        for (Object blueprint : modelFactory.getBlueprints()) {
            blueprintClasses.add(blueprint.getClass());
        }

        assertTrue("CarBlueprint should be registered", blueprintClasses.contains(CarBlueprint.class));
        assertTrue("SportsCarBlueprint should be registered", blueprintClasses.contains(SportsCarBlueprint.class));
        assertTrue("WheelBlueprint should be registered", blueprintClasses.contains(WheelBlueprint.class));
        assertTrue("DriverBlueprint should be registered", blueprintClasses.contains(DriverBlueprint.class));
        assertTrue("UserBlueprint should be registered", blueprintClasses.contains(UserBlueprint.class));
    }

    @Test
    public void springShouldInjectsBlueprint() {
        Erector erector = modelFactory.getErectors().get(SportsCar.class);
        assertNotNull( "ModelFactory should get injected from spring into Blueprint", ((SportsCarBlueprint) erector.getBlueprint()).modelFactory );
    }

    @Test
    public void springShouldInjectsModels() throws CreateModelException {
        SportsCar car = modelFactory.createModel(SportsCar.class);
        assertNotNull( "SpareTire should be injected from spring", car.getSpareTire() );
    }

}
