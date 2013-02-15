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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Field that is a List of Mapped Models
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MappedList {
    /**
     * Number of Models to be created by the ModelFactory and added to List, defaults to 1.
     * @return int
     */
	int size() default 1;

    /**
     * The target Blueprint Class used to create Models.
     * @return Class
     */
    Class target();

    /**
     * The List created, defaults to ArrayList
     * @return Class
     */
    Class targetList() default NotSet.class;

    /**
     * If true, do not create instances for an empty List. Defaults
     * to true.
     * @return
     */
    boolean ignoreEmpty() default true;
}
