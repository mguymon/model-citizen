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
import com.slackworks.modelcitizen.annotation.MappedList;
import com.slackworks.modelcitizen.field.DefaultField;
import com.slackworks.modelcitizen.field.MappedListField;
import com.slackworks.modelcitizen.field.MappedField;
import com.slackworks.modelcitizen.field.ModelField;
import com.slackworks.modelcitizen.policy.Policy;
import com.slackworks.modelcitizen.policy.PolicyException;
import com.slackworks.modelcitizen.policy.PolicyStatus;
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
	private Map<Class, List<Policy>> policies = new HashMap<Class, List<Policy>>();
	
	/**
	 * Create new instance
	 */
	public ModelFactory() {
		blueprints = new ArrayList<Blueprint>();
		erectors = new HashMap<Class,Erector>();
	}
	
	public void addPolicy( Policy policy ) throws PolicyException {
		if ( erectors.get( policy.getTarget() ) == null ) {
			throw new PolicyException( "Blueprint does not exist for Policy target: " + policy.getTarget() );
		}
		
		List<Policy> classPolicies = policies.get( policy.getTarget() );
		if ( classPolicies == null ) {
			classPolicies = new ArrayList<Policy>();
		}
		
		classPolicies.add( policy );
		
		logger.debug( "Setting Policy {} for {}", policy, policy.getTarget() );
		
		policies.put( policy.getTarget(), classPolicies );
	}
	
	/**
	 * Register a List of {@link Blueprint}, Class<Blueprint>, or String
	 * class names of Blueprint
	 * 
	 * @param blueprints List
	 * @throws RegisterBlueprintException
	 */
	public void setRegisterBlueprints( List blueprints ) throws RegisterBlueprintException {
		for( Object blueprint : blueprints ) {
			if ( blueprint instanceof Class ) {
				registerBlueprint( (Class)blueprint );
			} else if ( blueprint instanceof String ) {
				try {
					registerBlueprint( (Class<Blueprint>)Class.forName( (String)blueprint) );
				} catch (ClassNotFoundException e) {
					throw new RegisterBlueprintException(e);
				}
			} else {
				registerBlueprint( (Blueprint)blueprint );
			}
		}
	}
	
	/**
	 * Register a {@link Blueprint} from Class
	 * 
	 * @param clazz Blueprint class
	 * @throws RegisterBlueprintException
	 */
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
	
	/**
	 * Register {@link Blueprint} from instance.
	 * 
	 * @param blueprint {@link Blueprint}
	 * @throws RegisterBlueprintException
	 */
	public void registerBlueprint( Blueprint blueprint ) throws RegisterBlueprintException {
		
		List<ModelField> modelFields = new ArrayList<ModelField>();
		
		logger.info( "Registering blueprint for {} {}", blueprint.getTarget(), blueprint.getTarget().getAnnotations() );
		
		// Iterate Blueprint public fields for ModelCitizen annotations
		Field[] fields = blueprint.getClass().getFields();
		for( Field field: fields ) {
			
			logger.debug( "{} {}", field.getName(), field.getAnnotations() );
			
			// Process @Default
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
				
				// if class target not set  in annotation, use Field to determine class target
				if ( NotSet.class.equals( defaultField.getTarget() ) ) {
					
					 // If Field is a FieldCallBack, set the target to the FieldCallBack target
					 if ( FieldCallBack.class.equals( field.getType() ) ) {
						FieldCallBack callBack;
						try {
							callBack = ( FieldCallBack )field.get( blueprint );
						} catch (IllegalArgumentException e) {
							throw new RegisterBlueprintException(e);
						} catch (IllegalAccessException e) {
							throw new RegisterBlueprintException(e);
						}
						 defaultField.setTarget( callBack.getTarget() );
						 
					 // Set target to Field class
					 } else {
						 defaultField.setTarget( field.getType() );
					 }
				
				// Set target to annotation defined target
				} else {
					defaultField.setTarget( defaultField.getTarget() );
				}
				
				defaultField.setFieldClass( field.getType() );
				modelFields.add( defaultField );
				
				logger.info( "  Setting default for {} to {}", defaultField.getName(), defaultField.getValue() );
				
			}
			
			// Process @Mapped
			Mapped mapped = field.getAnnotation( Mapped.class ); 
			if ( mapped != null ) {
				MappedField mappedField = new MappedField();
				mappedField.setName( field.getName() );
				
				if ( NotSet.class.equals( mapped.target() ) ) {
					mappedField.setTarget( field.getType() );
				} else {
					mappedField.setTarget( mapped.target() );
				}
					
				mappedField.setFieldClass( field.getType() );
				modelFields.add( mappedField );
				
				logger.info( "  Setting mapped for {} to {}", mappedField.getName(), mappedField.getTarget());
			}
			
			// Process @MappedList
			MappedList mappedCollection = field.getAnnotation( MappedList.class );
			if ( mappedCollection != null ) {
				MappedListField listField = new MappedListField();
				listField.setName( field.getName() );
				listField.setFieldClass( field.getType() );
				listField.setSize( mappedCollection.size() );
				
				if ( NotSet.class.equals( mappedCollection.target() ) ) {
					listField.setTarget( field.getType() );
				} else {
					listField.setTarget( mappedCollection.target() );
				}
				
				modelFields.add( listField );
				
				logger.info( "  Setting mapped collection for {} to {} as <{}>", new Object[] { listField.getName(), listField.getFieldClass(), listField.getTarget() });
				
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
	 * Create a Model for a registered {@link Blueprint}
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
	 * Create a Model for a registered {@link Blueprint}. Values set in the
	 * model will not be overridden by defaults in the {@link Blueprint}.
	 * 
	 * @param model Object
	 * @return Model
	 * @throws CreateModelException
	 */
	public <T> T createModel( T model ) throws CreateModelException {
		return createModel( model, true );
	}
	
	/**
	 * Create a Model for a registered {@link Blueprint}. Values set in the
	 * model will not be overridden by defaults in the {@link Blueprint}.
	 * 
	 * @param model Object
	 * @return Model
	 * @throws CreateModelException
	 */
	public <T> T createModel( T model, boolean withPolicies ) throws CreateModelException {
		
		logger.debug( "Creating for {}", model );
		
		Erector erector = erectors.get( model.getClass() );
		
		if ( erector == null ) {
			throw new CreateModelException( "Unregistered class: " + model.getClass() );
		}
		
		for( ModelField modelField : erector.getModelFields() ) {
			
			logger.debug( "  ModelField {}", ReflectionToStringBuilder.toString(modelField) );
			
			
			boolean processModelField =  true;
			
			if  ( withPolicies ) {
				List<Policy> classPolicies = this.getPolicies().get( modelField.getTarget() );
				if ( classPolicies != null ) {
					
					logger.debug( "  Running policies" );
					
					for ( Policy policy : classPolicies ) {
						try {
							logger.info( "    processing {} for {}", policy, modelField.getTarget() );
							model = ( T )policy.process( this, erector.getBlueprint(), modelField, model );
						} catch (PolicyException e) {
							new CreateModelException(e);
						}
						
						if ( PolicyStatus.RETURN.equals( policy.getStatus() ) ) {
							processModelField = false;
						}
					}
				}
			}
			
			if ( processModelField ) {
				// Process DefaultField
				if ( modelField instanceof DefaultField ) {
					
					DefaultField defaultField = (DefaultField)modelField;
					
					Object value = null;
					try {
						value = erector.getBlueprint().getTemplate().get( model, defaultField );
					} catch (BlueprintTemplateException e) {
						throw new CreateModelException( e );
					} 
					
					// Use value set in the model, otherwise use value set in blueprint
					if ( value == null ) {
						value = defaultField.getValue();
					}
					
					// If value is an instance of FieldCallBack, eval the callback and use the value
					if ( value != null & value instanceof FieldCallBack ) {
						FieldCallBack callBack = (FieldCallBack)value;
						value = callBack.get( model );
					}
					
					defaultField.setValue( value );
					
					try {
						model = erector.getBlueprint().getTemplate().set( model, defaultField );
					} catch (BlueprintTemplateException e) {
						throw new CreateModelException( e );
					}
					
				// Process MappedField
			    } else if ( modelField instanceof MappedField ) {
					
					MappedField mappedField = (MappedField)modelField;
					
					Object value = null;
					try {
						value = erector.getBlueprint().getTemplate().get( model, mappedField );
					} catch (BlueprintTemplateException e) {
						// Get does not exist
					}
					
					if ( value == null ) {
						value = this.createModel( mappedField.getTarget() );
					} else {
						value = this.createModel( value );
					}
					
					mappedField.setValue( value );
					
					try {
						model = erector.getBlueprint().getTemplate().set( model, mappedField );
					} catch (BlueprintTemplateException e) {
						throw new CreateModelException( e );
					}
					
				// Process MappedListField
				} else if ( modelField instanceof MappedListField ) {
					
					MappedListField listField = (MappedListField)modelField;
					
					List modelList = null;
					List createdList = new ArrayList();
					
					try {
						modelList = (List)erector.getBlueprint().getTemplate().get( model, listField );
					} catch (BlueprintTemplateException e) {
						throw new CreateModelException( e );
					}
					
					if ( modelList == null ) {
						for ( int x = 0; x < listField.getSize(); x ++ ) {
							createdList.add( this.createModel( listField.getTarget() ) );
						}
					} else {
						for ( Object object : modelList ) {
							createdList.add( this.createModel( object ) );
						}
					}
					
					listField.setValue( createdList );
					
					try {
						model = erector.getBlueprint().getTemplate().set( model, listField );
					} catch (BlueprintTemplateException e) {
						throw new CreateModelException( e );
					}
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
	
	public Map<Class,List<Policy>> getPolicies() {
		return policies;
	}
}
