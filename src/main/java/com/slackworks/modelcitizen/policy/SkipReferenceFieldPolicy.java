package com.slackworks.modelcitizen.policy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slackworks.modelcitizen.Erector;
import com.slackworks.modelcitizen.ModelFactory;
import com.slackworks.modelcitizen.annotation.Blueprint;
import com.slackworks.modelcitizen.erector.Command;
import com.slackworks.modelcitizen.field.ModelField;
import com.slackworks.modelcitizen.template.BlueprintTemplateException;

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
	 * Prevents Model from being set by the reference model
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
