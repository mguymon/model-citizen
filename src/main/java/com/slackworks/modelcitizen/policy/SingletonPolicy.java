package com.slackworks.modelcitizen.policy;

import com.slackworks.modelcitizen.Blueprint;
import com.slackworks.modelcitizen.CreateModelException;
import com.slackworks.modelcitizen.ModelFactory;
import com.slackworks.modelcitizen.field.ModelField;
import com.slackworks.modelcitizen.template.BlueprintTemplateException;

public class SingletonPolicy extends Policy {

	private Class modelClass;
	private Object model;

	public SingletonPolicy( Class modelClass ) {
		super();
		this.modelClass = modelClass;
	}
	
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
		if ( this.getModel() == null ) {
			try {
				this.model = modelFactory.createModel( this.getTarget(), false );
			} catch (CreateModelException e) {
				throw new PolicyException( e );
			}
		}
		
		modelField.setValue( this.getModel() );
		try {
			destination = blueprint.getTemplate().set( destination, modelField);
		} catch (BlueprintTemplateException e) {
			throw new PolicyException( e );
		}
		
		return destination;
	}
	
	public PolicyStatus getStatus() {
		return PolicyStatus.RETURN;
	}

	@Override
	public Class getTarget() {
		return modelClass;
	}
}
