package com.slackworks.modelcitizen.template;

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

/**
 * BlueprintTemplate used to access the target Model
 */
public interface BlueprintTemplate {

	public <T> T construct( Class<T> modelClass ) throws BlueprintTemplateException;
	
	/**
	 * Set value of Model. Returns Model.
	 * 
	 * @param model T
	 * @param String property
	 * @params Object value
	 * @return T model
	 * @throws BlueprintTemplateException
	 */
	public <T> T set(T model, String property, Object value ) throws BlueprintTemplateException;
	
	/**
	 * Get value of Model
	 * 
	 * @param model Object
	 * @param String property
	 * @return Object get value
	 * @throws BlueprintTemplateException
	 */
	public Object get(Object model, String property ) throws BlueprintTemplateException;
}
