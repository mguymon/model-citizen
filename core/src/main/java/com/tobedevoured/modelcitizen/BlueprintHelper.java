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


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Helpers for Blueprint
 */
public class BlueprintHelper {

    private static ConcurrentHashMap<String, AtomicInteger> counters = new ConcurrentHashMap<String, AtomicInteger>();

    public static AtomicInteger counter(String name) {
        AtomicInteger counter = counters.get(name);

        if (counter == null) {
            counter = counters.putIfAbsent(name, new AtomicInteger(0));
            if ( counter == null ) {
                counter = counters.get(name);
            }
        }

        return counter;
    }

    public static AtomicInteger setCount(String name, int count) {
        AtomicInteger counter = counter(name);
        counter.set(count);
        return counter;
    }

    public static int currentCount(String name) {
        return counter(name).get();
    }

    public static int incrementCount(String name) {
        return counter(name).incrementAndGet();
    }

    public static int decrementCount(String name) {
        return counter(name).decrementAndGet();
    }
}
