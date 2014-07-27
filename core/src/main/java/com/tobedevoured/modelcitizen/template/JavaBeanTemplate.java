package com.tobedevoured.modelcitizen.template;

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

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.reflect.ConstructorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementation of BlueprintTemplate using Reflection for a JavaBean Model
 */
public class JavaBeanTemplate implements BlueprintTemplate {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @SuppressWarnings("unchecked")
	public <T> T construct( Class<T> modelClass ) throws BlueprintTemplateException {
		try {
			return (T)ConstructorUtils.invokeConstructor( modelClass, null );
		} catch (NoSuchMethodException e) {
			throw new BlueprintTemplateException( e );
		} catch (IllegalAccessException e) {
			throw new BlueprintTemplateException( e );
		} catch (InvocationTargetException e) {
			throw new BlueprintTemplateException( e );
		} catch (InstantiationException e) {
			throw new BlueprintTemplateException( e );
		}
	}

	public <T> T set( T model, String property, Object value ) throws BlueprintTemplateException {
		try {
			PropertyUtils.setProperty( model, property, value );
		} catch (IllegalAccessException propertyException) {
			throw new BlueprintTemplateException( propertyException );
		} catch (InvocationTargetException propertyException) {
			throw new BlueprintTemplateException( propertyException );
		} catch (NoSuchMethodException propertyException) {
			throw new BlueprintTemplateException( propertyException );
		}

		return model;
	}

	public Object get( Object model, String property ) throws BlueprintTemplateException {
        logger.trace( "Getting property [{}] for Model [{}]", property, model );
		try {
			return PropertyUtils.getProperty( model, property );
		} catch (IllegalAccessException propertyException) {
			throw new BlueprintTemplateException( propertyException );
		} catch (InvocationTargetException propertyException) {
			throw new BlueprintTemplateException( propertyException );
		} catch (NoSuchMethodException propertyException) {
			throw new BlueprintTemplateException( propertyException );
		}
	}

}
