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

import java.util.*;

import com.tobedevoured.modelcitizen.erector.Command;
import com.tobedevoured.modelcitizen.field.ConstructorCallback;
import com.tobedevoured.modelcitizen.field.ModelField;
import com.tobedevoured.modelcitizen.template.BlueprintTemplate;
import com.tobedevoured.modelcitizen.template.BlueprintTemplateException;

/**
 * Erector for a Class to create an instance from a {@link com.tobedevoured.modelcitizen.annotation.Blueprint} annotated Class
 */
public class Erector {

	private static final Set<Command> EMPTY_SET = new HashSet<Command>();
	
	private Object blueprint;
	private Map<String,ModelField> modelFields;
	private Map<ModelField,Set<Command>> modelFieldCommands = new HashMap<ModelField,Set<Command>>();
	private BlueprintTemplate blueprintTemplate;
	private Class target;
	private ConstructorCallback newInstance;
	private Object reference;

    public Erector() {
        modelFields = new HashMap<String,ModelField>();
    }

	/**
	 * Create new instance of {@link Erector#getTarget()}. Uses {@link #getNewInstance()}
	 * if defined, otherwise fails back to {@link BlueprintTemplate#construct}
	 * 
	 * @return Object new instance
	 * @throws BlueprintTemplateException 
	 */
	public Object createNewInstance() throws BlueprintTemplateException {
		if ( this.getNewInstance() != null ) {
			return this.getNewInstance().createInstance();
		} else {
			return this.getTemplate().construct( this.getTarget() );
		}
	}
	
	public void addCommands(ModelField modelField, Set<Command> commands) {
		for( Command command : commands ) {
			addCommand( modelField, command );
		}
	}
	
	public void addCommand( ModelField modelField, Command command ) {
		Set<Command> commands = modelFieldCommands.get( modelField );
		if ( commands == null ) {
			commands = new HashSet<Command>();
		}
		
		commands.add( command );
		modelFieldCommands.put( modelField, commands );
	}
	
	public Set<Command> getCommands( ModelField modelField ) {
		Set<Command> commands = modelFieldCommands.get( modelField );
		if ( commands != null ) {
			return commands;
		} else {
			return EMPTY_SET;
		}
	}
	
	public void clearCommands() {
		modelFieldCommands = new HashMap<ModelField,Set<Command>>();
	}
	
	public Object getBlueprint() {
		return blueprint;
	}
	
	public void setBlueprint(Object blueprint) {
		this.blueprint = blueprint;
	}
	
	public Collection<ModelField> getModelFields() {
		return modelFields.values();
	}

    public ModelField getModelField(String name) {
        return modelFields.get(name);
    }
	
	public void setModelFields(Collection<ModelField> modelFields) {
        for( ModelField modelField: modelFields ) {
            addModelField(modelField);
        }
    }

    public void addModelField(ModelField modelField) {
		this.modelFields.put(modelField.getName(), modelField);
	}
	
	public BlueprintTemplate getTemplate() {
		return blueprintTemplate;
	}

	public void setTemplate(BlueprintTemplate blueprintTemplate) {
		this.blueprintTemplate = blueprintTemplate;
	}

	public Class getTarget() {
		return target;
	}
	
	public void setTarget(Class target) {
		this.target = target;
	}

	public Object getReference() {
		return reference;
	}

	public void setReference(Object reference) {
		this.reference = reference;
	}

	public ConstructorCallback getNewInstance() {
		return newInstance;
	}

	public void setNewInstance(ConstructorCallback newInstance) {
		this.newInstance = newInstance;
	}
	
}
