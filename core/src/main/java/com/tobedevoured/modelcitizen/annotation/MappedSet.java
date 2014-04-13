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
public @interface MappedSet {
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
     * The Set created, defaults to HashSet.
     * @return Class
     */
    Class targetSet() default NotSet.class;

    /**
     * If true, do not create instances for an empty Set. Defaults
     * to true.
     * @return boolean
     */
    boolean ignoreEmpty() default true;

    /**
     * Force the value of the MappedSet to always be set, even if
     * the target field already has a value. Default is false.
     * @return boolean
     */
    boolean force() default false;
}
