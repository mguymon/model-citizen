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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tobedevoured.modelcitizen.CreateModelException;
import com.tobedevoured.modelcitizen.Erector;
import com.tobedevoured.modelcitizen.ModelFactory;
import com.tobedevoured.modelcitizen.erector.Command;
import com.tobedevoured.modelcitizen.field.MappedField;
import com.tobedevoured.modelcitizen.field.ModelField;
import com.tobedevoured.modelcitizen.template.BlueprintTemplateException;

/**
 * Enforce a @Mapped field in a @Blueprint as a Singleton {@link Policy}
 * for creating models.
 *
 * If constructed with a Class, the first attempt to set @Mapped instance of Class
 * will use ModelFactory to create singleton to use
 * for all instances of Class in registered Blueprints. Note:
 * the ModelFactory will not run any Policy for the creation.
 *
 * If constructed with a Model, the Model will be used for all @Mapped instances of
 * Model's class in registered Blueprints.
 *
 */
public class MappedSingletonPolicy implements FieldPolicy {

	private Logger logger = LoggerFactory.getLogger( this.getClass() );

	private Class singletonClass;
	private Object singleton;

	/**
	 * Create new Singleton with from a registered Class.
 	 *
	 * @param singletonClass Class
	 */
	public MappedSingletonPolicy( Class singletonClass ) {
		super();
		this.singletonClass = singletonClass;
	}

	/**
	 * Create new instance with Model.
	 *
	 * @param model Object
	 */
	public MappedSingletonPolicy( Object model ) {
		super();

		this.singleton = model;
		this.singletonClass = this.singleton.getClass();
	}

	public Object getSingleton() {
		return singleton;
	}

	public void setSingleton(Object singleton) {
		this.singleton = singleton;
	}

	public Command process(ModelFactory modelFactory, Erector erector, ModelField modelField, Object model) throws PolicyException {

		logger.debug( "processing {} for {}", modelField, model );

		// If Model has not be set, create a new one from ModelFactory
		if ( modelField instanceof MappedField ) {
			if ( this.getSingleton() == null ) {
				logger.debug("  creating singleton for {}", this.getTarget() );
				try {
					this.setSingleton( modelFactory.createModel( this.getTarget(), false ) );
				} catch (CreateModelException e) {
					throw new PolicyException( e );
				}
			}

			// Set Singleton into model
			try {
				erector.getTemplate().set( model, modelField.getName(), this.getSingleton() );
			} catch (BlueprintTemplateException e) {
				throw new PolicyException(e);
			}
		}

		return Command.SKIP_INJECTION;
	}

	public Class getTarget() {
		return singletonClass;
	}
}
