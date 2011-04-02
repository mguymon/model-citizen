package com.slackworks.modelcitizen;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slackworks.modelcitizen.annotation.Default;
import com.slackworks.modelcitizen.annotation.Mapped;
import com.slackworks.modelcitizen.field.DefaultField;
import com.slackworks.modelcitizen.field.MappedField;
import com.slackworks.modelcitizen.field.ModelField;
import com.slackworks.modelcitizen.template.BlueprintTemplateException;

/**
 * ModelFactory for generating Models. A Model's {@link Blueprint} is registered
 * with the ModelFactory. Then a Model can be generated with {@link #createModel(Class)}
 * or {@link #createModel(Object)}
 */
public class ModelFactory {

	private Logger logger = LoggerFactory.getLogger( this.getClass() );
	
	private List<Blueprint> blueprints;
	private Map<Class,Erector> erectors = new HashMap<Class,Erector>();
	
	public ModelFactory() {
		blueprints = new ArrayList<Blueprint>();
		erectors = new HashMap<Class,Erector>();
	}
	
	public void registerBlueprint( Class<Blueprint> clazz ) throws RegisterBlueprintException {
		Blueprint blueprint = null;
		
		try {
			blueprint = clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RegisterBlueprintException(e);
		} catch (IllegalAccessException e) {
			throw new RegisterBlueprintException(e);
		}
		
		registerBlueprint( blueprint );
	}
	
	public void registerBlueprint( Blueprint blueprint ) throws RegisterBlueprintException {
		
		List<ModelField> modelFields = new ArrayList<ModelField>();
		
		logger.info( "Building blueprint for {} {}", blueprint.getTarget(), blueprint.getTarget().getAnnotations() );
		
		Field[] fields = blueprint.getClass().getFields();
		
		for( Field field: fields ) {
			logger.debug( "{} {}", field.getName(), field.getAnnotations() );
			if ( field.getAnnotation( Default.class ) != null ) {
				
				DefaultField defaultField = new DefaultField();
				defaultField.setName( field.getName() );
				try {
					defaultField.setValue( field.get( blueprint ) );
				} catch (IllegalArgumentException e) {
					throw new RegisterBlueprintException( e );
				} catch (IllegalAccessException e) {
					throw new RegisterBlueprintException( e );
				}
				defaultField.setFieldClass( field.getType() );
				modelFields.add( defaultField );
				
				logger.info( "Setting default for {} to {}", defaultField.getName(), defaultField.getValue() );
				
			}
			
			if ( field.getAnnotation( Mapped.class )  != null ) {
				MappedField mappedField = new MappedField();
				mappedField.setName( field.getName() );
				
				mappedField.setFieldClass( field.getType() );
				modelFields.add( mappedField );
				
				logger.info( "Setting mapped for {} to {}", mappedField.getName(), mappedField.getFieldClass());
			}
		}
		
		blueprints.add( blueprint );
		
		Erector erector = new Erector();
		erector.setBlueprint( blueprint );
		erector.setModelFields( modelFields );
		erector.setTargetClass( blueprint.getTarget() );
		
		erectors.put( blueprint.getTarget(), erector );
	}
	
	/**
	 * Create a Model from a registered {@link Blueprint}
	 * 
	 * @param clazz Model class
	 * @return Model
	 * @throws CreateModelException
	 */
	public <T> T createModel( Class<T> clazz ) throws CreateModelException {
		try {
			return createModel( clazz.newInstance() );
		} catch (InstantiationException e) {
			throw new CreateModelException( e );
		} catch (IllegalAccessException e) {
			throw new CreateModelException( e );
		}
	}
	
	/**
	 * Create a Model from a registered {@link Blueprint}. Values set in the
	 * model will not be overridden by defaults set in the {@link Blueprint}.
	 * 
	 * @param model Object
	 * @return Model
	 * @throws CreateModelException
	 */
	public <T> T createModel( T model ) throws CreateModelException {
		
		logger.info( "Creating for {}", model.getClass() );
		
		Erector erector = erectors.get( model.getClass() );
		
		if ( erector == null ) {
			throw new CreateModelException( "Unregistered class: " + model.getClass() );
		}
		
		for( ModelField modelField : erector.getModelFields() ) {
			
			logger.debug( "ModelField {}", ReflectionToStringBuilder.toString(modelField) );

			
			if ( modelField instanceof DefaultField ) {
				
				DefaultField defaultField = (DefaultField)modelField;
				
				Object value = null;
				try {
					value = erector.getBlueprint().getTemplate().get( model, defaultField );
				} catch (BlueprintTemplateException e) {
					throw new CreateModelException( e );
				} 
				
				// Use value set in the model, otherwise use value set in blueprint
				if ( value != null ) {
					defaultField.setValue( value );
				}
				
				try {
					model = erector.getBlueprint().getTemplate().set( model, defaultField );
				} catch (BlueprintTemplateException e) {
					throw new CreateModelException( e );
				}
				
			} else if ( modelField instanceof MappedField ) {
				
				MappedField mappedField = (MappedField)modelField;
				
				Object value = null;
				try {
					value = erector.getBlueprint().getTemplate().get( model, mappedField );
				} catch (BlueprintTemplateException e) {
					throw new CreateModelException( e );
				}
				
				if ( value == null ) {
					value = this.createModel( mappedField.getFieldClass() );
				} else {
					value = this.createModel( value );
				}
				
				mappedField.setValue( value );
				
				try {
					model = erector.getBlueprint().getTemplate().set( model, mappedField );
				} catch (BlueprintTemplateException e) {
					throw new CreateModelException( e );
				}
			}
		}
		
		return model;
	}
	
	/**
	 * Registered Blueprints
	 * 
	 * @return {@link List<Blueprint>}
	 */
	public List<Blueprint> getBlueprints() {
		return blueprints;
	}
	
	/**
	 * Map of Class to their {@link Erector}.
	 * 
	 * @return {@link Map<Class, Erector>}
	 */
	public Map<Class,Erector> getErectors() {
		return erectors;
	}
}
