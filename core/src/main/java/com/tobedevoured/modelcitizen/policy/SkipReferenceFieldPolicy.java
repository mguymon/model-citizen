package com.tobedevoured.modelcitizen.policy;

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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tobedevoured.modelcitizen.Erector;
import com.tobedevoured.modelcitizen.ModelFactory;
import com.tobedevoured.modelcitizen.annotation.Blueprint;
import com.tobedevoured.modelcitizen.erector.Command;
import com.tobedevoured.modelcitizen.field.ModelField;

/**
 * Always set a Field from @Blueprint
 */
public class SkipReferenceFieldPolicy implements BlueprintPolicy {

	private Logger logger = LoggerFactory.getLogger( this.getClass() );
	
	private String field;
	private Class target;
	
	/**
	 * Create new instance with String field that will be set and the Class
	 * targeted by a {@link Blueprint#value()} 
	 * 
	 * @param field String
	 * @param target Class
	 */
	public SkipReferenceFieldPolicy( String field, Class target ) {
		super();
		this.field = field;
		this.target = target;
	}
	
	public Class getTarget() {
		return target;
	}

	/**
	 * Prevents Model from being set by the Reference Model
	 */
	public Map<ModelField,Set<Command>> process(ModelFactory modelFactory, Erector erector, Object model) throws PolicyException {
		
		Map<ModelField,Set<Command>> modelFieldCommands = new HashMap<ModelField,Set<Command>>();
		
		for ( ModelField modelField : erector.getModelFields() ) {

			logger.debug( "    {} {}", getTarget(), modelField);
			
			if ( modelField.getName().equals( field ) ) {
				
				Set<Command> commands = modelFieldCommands.get( modelField );
				if ( commands == null ) {
					commands = new HashSet<Command>();
				}
				commands.add( Command.SKIP_REFERENCE_INJECTION );
				modelFieldCommands.put( modelField, commands );
			}
		}
		
		return modelFieldCommands;
	}
}
