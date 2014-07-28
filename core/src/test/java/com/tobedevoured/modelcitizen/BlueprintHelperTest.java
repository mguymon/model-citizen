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

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

import static com.tobedevoured.modelcitizen.BlueprintHelper.*;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.junit.Assert.assertEquals;

public class BlueprintHelperTest {


    @Test
    public void testIncrementCount() {
        assertEquals( 1, incrementCount("test") );
        assertEquals( 2, incrementCount("test") );
        assertEquals( 3, incrementCount("test") );
    }

    @Test
    public void threadyIncrementCount() throws InterruptedException, ExecutionException {
        int threadCount = 10;

        Callable<Integer> task = new Callable<Integer>() {
            @Override
            public Integer call() {
                return incrementCount("thready");
            }
        };
        List<Callable<Integer>> tasks = Collections.nCopies(threadCount, task);
        ExecutorService executorService = newFixedThreadPool(threadCount);
        List<Future<Integer>> futures = executorService.invokeAll(tasks);
        List<Integer> resultList = new ArrayList<Integer>(futures.size());
        // Check for exceptions
        for (Future<Integer> future : futures) {
            // Throws an exception if an exception was thrown by the task.
            resultList.add(future.get());
        }
        // Validate the IDs
        Assert.assertEquals(threadCount, futures.size());
        List<Integer> expectedList = new ArrayList<Integer>(threadCount);
        for (int i = 1; i <= threadCount; i++) {
            expectedList.add(i);
        }
        Collections.sort(resultList);
        Assert.assertEquals(expectedList, resultList);
    }

    @Test
    public void threadyDecrementCount() throws InterruptedException, ExecutionException {
        int threadCount = 12;

        setCount("thready", threadCount);
        assertEquals(threadCount, currentCount("thready"));

        Callable<Integer> task = new Callable<Integer>() {
            @Override
            public Integer call() {
                return decrementCount("thready");
            }
        };
        List<Callable<Integer>> tasks = Collections.nCopies(threadCount, task);
        ExecutorService executorService = newFixedThreadPool(threadCount);
        List<Future<Integer>> futures = executorService.invokeAll(tasks);
        List<Integer> resultList = new ArrayList<Integer>(futures.size());
        // Check for exceptions
        for (Future<Integer> future : futures) {
            // Throws an exception if an exception was thrown by the task.
            resultList.add(future.get());
        }
        // Validate the IDs
        Assert.assertEquals(threadCount, futures.size());
        List<Integer> expectedList = new ArrayList<Integer>(threadCount);
        for (int i = threadCount -1; i >= 0; i--) {
            expectedList.add(i);
        }
        Collections.sort(resultList);

        assertEquals(0, currentCount("thready"));

        // XXX: why does this need reversed?
        Collections.reverse(resultList);
        assertEquals(expectedList, resultList);
    }
}
