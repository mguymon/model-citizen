package com.tobedevoured.modelcitizen.blueprint;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.tobedevoured.modelcitizen.annotation.Blueprint;
import com.tobedevoured.modelcitizen.annotation.Default;
import com.tobedevoured.modelcitizen.annotation.Mapped;
import com.tobedevoured.modelcitizen.annotation.MappedList;
import com.tobedevoured.modelcitizen.annotation.MappedSet;
import com.tobedevoured.modelcitizen.annotation.Nullable;
import com.tobedevoured.modelcitizen.callback.AfterCreateCallback;
import com.tobedevoured.modelcitizen.model.Car;
import com.tobedevoured.modelcitizen.model.Driver;
import com.tobedevoured.modelcitizen.model.Wheel;

@Blueprint(Car.class)
public class CarBlueprint {

    @Default
    public String make = "car make";

    @Default
    public String manufacturer = "car manufacturer";

    @Default
    public float mileage = 100.1f;

    @Default
    public Map status = new HashMap();

    @MappedList(target = Wheel.class, size = 4, force = true)
    public List<Wheel> wheels;

    @MappedSet(target = Wheel.class, size = 1)
    public Set<Wheel> spares;

    @Mapped
    public Driver driver;

    @Mapped
    @Nullable
    public Driver passenger = null;

    // Set the Car for each of the Car's wheels
    AfterCreateCallback<Car> afterCreate = new AfterCreateCallback<Car>() {
        @Override
        public Car afterCreate(Car model) {
            for(Wheel wheel : model.getWheels() ) {
                wheel.setCar(model);
            }

            return model;
        }
    };

}
