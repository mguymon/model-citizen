package com.slackworks.modelcitizen.policy;

import com.slackworks.modelcitizen.Blueprint;
import com.slackworks.modelcitizen.CreateModelException;
import com.slackworks.modelcitizen.ModelFactory;
import com.slackworks.modelcitizen.field.ModelField;
import com.slackworks.modelcitizen.template.BlueprintTemplateException;

/**
 * Singleton {@link Policy} for creating models. 
 * 
 * If constructed with a Class, the first attempt to set instance of Class
 * will use {@link ModelFactory#createModel(Class)} to create singleton to use 
 * for all instances of Class in registered {@link Blueprint}s. 
 * 
 * If constructed with a Model, the Model will be used for all instances of
 * Model's class in registered {@link Blueprint}s.
 * 
 */
public class SingletonPolicy extends Policy {

	private Class modelClass;
	private Object model;

	/**
	 * Create new instace with a Class. The first attempt to set instance of Class
	 * will use {@link ModelFactory#createModel(Class)} to create singleton to use 
 	 * for all instances of Class in registered {@link Blueprint}s. 
 	 *	 
	 * @param modelClass Class
	 */
	public SingletonPolicy( Class modelClass ) {
		super();
		this.modelClass = modelClass;
	}
	
	/**
	 * Create new instance with Model. Model will be used for all instances of
	 * Model's class in registered {@link Blueprint}s.
	 * 
	 * @param model Object
	 */
	public SingletonPolicy( Object model ) {
		super();
		
		this.model = model;
		this.modelClass = this.model.getClass();
	}
	
	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}

	@Override
	public Object process(ModelFactory modelFactory, Blueprint blueprint, ModelField modelField, Object destination) throws PolicyException {
		// If Model has not be set, create a new one from ModelFactory
		if ( this.getModel() == null ) {
			try {
				this.model = modelFactory.createModel( this.getTarget(), false );
			} catch (CreateModelException e) {
				throw new PolicyException( e );
			}
		}
		
		// Set Model into ModelField, then update destination with Model
		modelField.setValue( this.getModel() );
		try {
			destination = blueprint.getTemplate().set( destination, modelField);
		} catch (BlueprintTemplateException e) {
			throw new PolicyException( e );
		}
		
		return destination;
	}
	
	/**
	 * @return {@linkplain PolicyStatus#RETURN}
	 */
	public PolicyStatus getStatus() {
		return PolicyStatus.RETURN;
	}

	@Override
	public Class getTarget() {
		return modelClass;
	}
}
