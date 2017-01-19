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

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.metapossum.utils.scanner.reflect.ClassesInPackageScanner;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.lang.reflect.ConstructorUtils;
import com.tobedevoured.modelcitizen.annotation.Blueprint;
import com.tobedevoured.modelcitizen.annotation.Default;
import com.tobedevoured.modelcitizen.annotation.Mapped;
import com.tobedevoured.modelcitizen.annotation.MappedList;
import com.tobedevoured.modelcitizen.annotation.MappedSet;
import com.tobedevoured.modelcitizen.annotation.NotSet;
import com.tobedevoured.modelcitizen.annotation.Nullable;
import com.tobedevoured.modelcitizen.erector.Command;
import com.tobedevoured.modelcitizen.policy.BlueprintPolicy;
import com.tobedevoured.modelcitizen.policy.FieldPolicy;
import com.tobedevoured.modelcitizen.policy.Policy;
import com.tobedevoured.modelcitizen.policy.PolicyException;
import com.tobedevoured.modelcitizen.template.BlueprintTemplate;
import com.tobedevoured.modelcitizen.template.BlueprintTemplateException;
import com.tobedevoured.modelcitizen.template.JavaBeanTemplate;
import com.tobedevoured.modelcitizen.callback.AfterCreateCallback;
import com.tobedevoured.modelcitizen.callback.Callback;
import com.tobedevoured.modelcitizen.callback.ConstructorCallback;
import com.tobedevoured.modelcitizen.callback.internal.Constructable;
import com.tobedevoured.modelcitizen.callback.internal.Getable;
import com.tobedevoured.modelcitizen.field.*;

/**
 * ModelFactory for generating Models. A Model's Blueprint is registered
 * with the ModelFactory. Then a Model can be generated with {@link #createModel(Class)}
 * or {@link #createModel(Object)}
 */
public class ModelFactory {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<Object> blueprints;
    private Map<Class, Erector> erectors = new HashMap<Class, Erector>();
    private Map<Class, List<FieldPolicy>> fieldPolicies = new HashMap<Class, List<FieldPolicy>>();
    private Map<Class, List<BlueprintPolicy>> blueprintPolicies = new HashMap<Class, List<BlueprintPolicy>>();

    /**
     * Create new instance
     */
    public ModelFactory() {
        blueprints = new ArrayList<Object>();
        erectors = new HashMap<Class, Erector>();
    }

    /**
     * Add Policy to ModelFactory
     *
     * @param policy FieldPolicy or BlueprintPolicy
     * @throws PolicyException failed to apply policy
     */
    public void addPolicy(Policy policy) throws PolicyException {

        // Add BlueprintPolicy
        if (policy instanceof BlueprintPolicy) {
            if (erectors.get(policy.getTarget()) == null) {
                throw new PolicyException("Blueprint does not exist for BlueprintPolicy target: " + policy.getTarget());
            }

            List<BlueprintPolicy> policies = blueprintPolicies.get(policy.getTarget());
            if (policies == null) {
                policies = new ArrayList<BlueprintPolicy>();
            }

            policies.add((BlueprintPolicy) policy);

            logger.info("Setting BlueprintPolicy {} for {}", policy, policy.getTarget());

            blueprintPolicies.put(policy.getTarget(), policies);

            // Add FieldPolicy
        } else if (policy instanceof FieldPolicy) {

            // XXX: force FieldPolicy's to be mapped to a blueprint? Limits their scope, but enables validation
            if (erectors.get(policy.getTarget()) == null) {
                throw new PolicyException("Blueprint does not exist for FieldPolicy target: " + policy.getTarget());
            }

            List<FieldPolicy> policies = fieldPolicies.get(policy.getTarget());
            if (policies == null) {
                policies = new ArrayList<FieldPolicy>();
            }

            policies.add((FieldPolicy) policy);

            logger.info("Setting FieldPolicy {} for {}", policy, policy.getTarget());

            fieldPolicies.put(policy.getTarget(), policies);
        }
    }

    /**
     * Register all Blueprint in package.
     *
     * @param _package String package to scan
     * @throws RegisterBlueprintException failed to register blueprint
     */
    public void setRegisterBlueprintsByPackage(String _package) throws RegisterBlueprintException {

        Set<Class<?>> annotated = null;
        try {
            annotated = new ClassesInPackageScanner().findAnnotatedClasses(_package, Blueprint.class);
        } catch (IOException e) {
            throw new RegisterBlueprintException(e);
        }

        logger.info("Scanned {} and found {}", _package, annotated);

        this.setRegisterBlueprints(annotated);
    }

    /**
     * Register a List of Blueprint, Class, or String
     * class names of Blueprint
     *
     * @param blueprints List
     * @throws RegisterBlueprintException failed to register blueprint
     */
    public void setRegisterBlueprints(Collection blueprints) throws RegisterBlueprintException {
        for (Object blueprint : blueprints) {
            if (blueprint instanceof Class) {
                registerBlueprint((Class) blueprint);
            } else if (blueprint instanceof String) {
                registerBlueprint((String) blueprint);
            } else if (blueprint instanceof String) {
                registerBlueprint(blueprint);
            } else {
                throw new RegisterBlueprintException("Only supports List comprised of Class<Blueprint>, Blueprint, or String className");
            }
        }
    }

    /**
     * Register a Blueprint from a String Class name
     *
     * @param className String
     */
    public void registerBlueprint(String className) throws RegisterBlueprintException {
        try {
            registerBlueprint(Class.forName(className));
        } catch (ClassNotFoundException e) {
            throw new RegisterBlueprintException(e);
        }
    }

    /**
     * Register a Blueprint from Class
     *
     * @param clazz Blueprint class
     * @throws RegisterBlueprintException failed to register blueprint
     */
    public void registerBlueprint(Class clazz) throws RegisterBlueprintException {
        Object blueprint = null;

        try {
            blueprint = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RegisterBlueprintException(e);
        } catch (IllegalAccessException e) {
            throw new RegisterBlueprintException(e);
        }

        registerBlueprint(blueprint);
    }

    /**
     * Register Blueprint from instance.
     *
     * @param blueprint Blueprint
     * @throws RegisterBlueprintException failed to register blueprint
     */
    public void registerBlueprint(Object blueprint) throws RegisterBlueprintException {

        Blueprint blueprintAnnotation = blueprint.getClass().getAnnotation(Blueprint.class);
        if (blueprintAnnotation == null) {
            throw new RegisterBlueprintException("Blueprint class not annotated by @Blueprint: " + blueprint);
        }
        Class target = blueprintAnnotation.value();

        List<ModelField> modelFields = new ArrayList<ModelField>();

        logger.debug("Registering {} blueprint for {}", blueprint.getClass(), target);

        Constructable newInstance = null;

        List<Callback> afterCreateCallbacks = new ArrayList<Callback>();

        // Get all fields for the blueprint target class
        Collection<Field> fields = getAllFields(blueprint.getClass()).values();
        for (Field field : fields) {

            field.setAccessible(true);

            // Register ConstructorCallback field
            if ( field.getType().equals(ConstructorCallback.class) || field.getType().equals(com.tobedevoured.modelcitizen.callback.ConstructorCallback.class)) {
                Object fieldVal = null;
                try {
                    fieldVal = field.get(blueprint);
                } catch (IllegalArgumentException e) {
                    throw new RegisterBlueprintException(e);
                } catch (IllegalAccessException e) {
                    throw new RegisterBlueprintException(e);
                }

                if (fieldVal instanceof Constructable) {
                    logger.debug("Registering ConstructorCallback for {}", blueprint.getClass());
                    newInstance = (Constructable) fieldVal;
                } else {
                    throw new RegisterBlueprintException("Blueprint " + blueprint.getClass().getSimpleName() + " Field class for " + field.getName() + " is invalid ConstructorCallback");
                }

                // ConstructorCallback is only used to create new instance.
                continue;
            }

            // Register AfterCreateCallback field
            if ( field.getType().equals(AfterCreateCallback.class) ) {

                Object fieldVal = null;
                try {
                    fieldVal = field.get(blueprint);
                } catch (IllegalArgumentException e) {
                    throw new RegisterBlueprintException(e);
                } catch (IllegalAccessException e) {
                    throw new RegisterBlueprintException(e);
                }

                if (fieldVal instanceof AfterCreateCallback) {
                    logger.debug("Registering AfterCreateCallback for {}", blueprint.getClass());
                    afterCreateCallbacks.add((AfterCreateCallback)fieldVal);
                } else {
                    throw new RegisterBlueprintException("Blueprint " + blueprint.getClass().getSimpleName() + " Field class for " + field.getName() + " is invalid AfterCreateCallback");
                }

                // AfterCreateCallback is only used in callbacks
                continue;
            }

            // Process @Default
            Default defaultAnnotation = field.getAnnotation(Default.class);
            if (defaultAnnotation != null) {

                DefaultField defaultField = new DefaultField();
                defaultField.setName(field.getName());
                defaultField.setForce(defaultAnnotation.force());

                try {
                    defaultField.setValue(field.get(blueprint));
                } catch (IllegalArgumentException e) {
                    throw new RegisterBlueprintException(e);
                } catch (IllegalAccessException e) {
                    throw new RegisterBlueprintException(e);
                }

                defaultField.setTarget(field.getType());
                defaultField.setFieldClass(field.getType());
                modelFields.add(defaultField);

                logger.trace("  Setting default for {} to {} and forced {}", new Object[]{defaultField.getName(), defaultField.getValue(), defaultField.isForce()});

            }

            // Process @Mapped
            Mapped mapped = field.getAnnotation(Mapped.class);
            if (mapped != null) {
                MappedField mappedField = new MappedField();
                mappedField.setName(field.getName());

                if (field.getAnnotation(Nullable.class) != null) {
                    mappedField.setNullable(true);
                }

                // If @Mapped(target) not set, use Field's class
                if (NotSet.class.equals(mapped.target())) {
                    mappedField.setTarget(field.getType());

                    // Use @Mapped(target) for MappedField#target
                } else {
                    mappedField.setTarget(mapped.target());
                }

                mappedField.setFieldClass(field.getType());
                modelFields.add(mappedField);

                logger.trace("  Setting mapped for {} to {}", mappedField.getName(), mappedField.getTarget());
            }

            // Process @MappedList
            MappedList mappedCollection = field.getAnnotation(MappedList.class);
            if (mappedCollection != null) {
                MappedListField listField = new MappedListField();
                listField.setName(field.getName());
                listField.setFieldClass(field.getType());
                listField.setSize(mappedCollection.size());
                listField.setIgnoreEmpty(mappedCollection.ignoreEmpty());
                listField.setForce(mappedCollection.force());

                // If @MappedList(target) not set, use Field's class
                if (NotSet.class.equals(mappedCollection.target())) {
                    listField.setTarget(field.getType());

                    // Use @MappedList(target) for MappedListField#target
                } else {
                    listField.setTarget(mappedCollection.target());
                }

                // If @MappedList(targetList) not set, use ArrayList
                if (NotSet.class.equals(mappedCollection.targetList())) {
                    listField.setTargetList(ArrayList.class);
                } else {

                    // Ensure that the targetList implements List
                    boolean implementsList = false;
                    for (Class interf : mappedCollection.targetList().getInterfaces()) {
                        if (List.class.equals(interf)) {
                            implementsList = true;
                            break;
                        }
                    }

                    if (!implementsList) {
                        throw new RegisterBlueprintException("@MappedList targetList must implement List for field " + field.getName());
                    }

                    listField.setTargetList(mappedCollection.targetList());
                }

                modelFields.add(listField);

                logger.trace("  Setting mapped list for {} to {} as <{}> and forced {}", new Object[]{listField.getName(), listField.getFieldClass(), listField.getTarget(), listField.isForce()});

            }

            // Process @MappedSet
            MappedSet mappedSet = field.getAnnotation(MappedSet.class);
            if (mappedSet != null) {
                MappedSetField setField = new MappedSetField();
                setField.setName(field.getName());
                setField.setFieldClass(field.getType());
                setField.setSize(mappedSet.size());
                setField.setIgnoreEmpty(mappedSet.ignoreEmpty());
                setField.setForce(mappedSet.force());

                // XXX: @MappedSet( target ) is required
                // If @MappedSet(target) not set
                if (NotSet.class.equals(mappedSet.target())) {

                    // XXX: incorrect, should use generic defined by Set, luckily annotation forces target to be set
                    setField.setTarget(field.getType());

                    // Use @MappedSet(target) for MappedSet#target
                } else {
                    setField.setTarget(mappedSet.target());
                }

                // If @MappedSet(targetSet) not set, use HashSet
                if (NotSet.class.equals(mappedSet.targetSet())) {
                    setField.setTargetSet(HashSet.class);
                } else {

                    // Ensure that the targetSet implements Set
                    boolean implementsSet = false;
                    for (Class interf : mappedSet.targetSet().getInterfaces()) {
                        if (Set.class.equals(interf)) {
                            implementsSet = true;
                            break;
                        }
                    }

                    if (!implementsSet) {
                        throw new RegisterBlueprintException("@MappedSet targetSet must implement Set for field " + field.getName());
                    }

                    setField.setTargetSet(mappedSet.targetSet());
                }

                modelFields.add(setField);

                logger.trace("  Setting mapped set for {} to {} as <{}> and is forced {}", new Object[]{setField.getName(), setField.getFieldClass(), setField.getTarget(), setField.isForce()});

            }
        }

        blueprints.add(blueprint);

        Class templateClass = blueprintAnnotation.template();
        BlueprintTemplate template = null;
        try {
          template = (BlueprintTemplate)ConstructorUtils.invokeConstructor( templateClass, null );
        } catch (NoSuchMethodException e) {
          throw new RegisterBlueprintException( e );
        } catch (IllegalAccessException e) {
          throw new RegisterBlueprintException( e );
        } catch (InvocationTargetException e) {
          throw new RegisterBlueprintException( e );
        } catch (InstantiationException e) {
          throw new RegisterBlueprintException( e );
        }

        // Create Erector for this Blueprint
        Erector erector = new Erector();
        erector.setTemplate(template);
        erector.setBlueprint(blueprint);
        erector.setModelFields(modelFields);
        erector.setTarget(target);
        erector.setNewInstance(newInstance);
        erector.setCallbacks("afterCreate", afterCreateCallbacks);

        erectors.put(target, erector);
    }

    /**
     * Create a Model for a registered Blueprint
     *
     * @param <T> model Class
     * @param clazz Model class
     * @return Model
     * @throws CreateModelException model failed to create
     */
    public <T> T createModel(Class<T> clazz) throws CreateModelException {
        return createModel(clazz, true);
    }

    /**
     * Create a Model for a registered Blueprint
     *
     * @param <T> model Class
     * @param clazz        Model class
     * @param withPolicies boolean if Policies should be applied to the create
     * @return Model
     * @throws CreateModelException model failed to create
     */
    public <T> T createModel(Class<T> clazz, boolean withPolicies) throws CreateModelException {
        Erector erector = erectors.get(clazz);

        if (erector == null) {
            throw new CreateModelException("Unregistered class: " + clazz);
        }

        return createModel(erector, null, withPolicies);
    }

    /**
     * Create a Model for a registered Blueprint. Values set in the
     * model will not be overridden by defaults in the Blueprint.
     *
     * @param <T> model Class
     * @param referenceModel Object
     * @return Model
     * @throws CreateModelException model failed to create
     */
    public <T> T createModel(T referenceModel) throws CreateModelException {
        return createModel(referenceModel, true);
    }

    /**
     * Create a Model for a registered Blueprint. Values set in the
     * model will not be overridden by defaults in the Blueprint.
     *
     * @param <T> model Class
     * @param referenceModel Object
     * @param withPolicies   boolean if Policies should be applied to the create
     * @return Model
     * @throws CreateModelException model failed to create
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <T> T createModel(T referenceModel, boolean withPolicies) throws CreateModelException {
        Erector erector = erectors.get(referenceModel.getClass());

        if (erector == null) {
            throw new CreateModelException("Unregistered class: " + referenceModel.getClass());
        }

        return createModel(erector, referenceModel, withPolicies);
    }

    /**
     * Create a Model for a registered Blueprint using Erector.
     * Values set in the model will not be overridden by defaults in the
     * Blueprint.
     *
     * @param <T>             model class
     * @param erector        Erector
     * @param referenceModel T the reference model instance, or null
     * @param withPolicies   boolean if Policies should be applied to the create
     * @return T new Model
     * @throws CreateModelException model failed to create
     */
    public <T> T createModel(Erector erector, T referenceModel, boolean withPolicies) throws CreateModelException {

        erector.clearCommands();

        T createdModel;
        try {
            createdModel = (T) createNewInstance(erector);
        } catch (BlueprintTemplateException e) {
            throw new CreateModelException(e);
        }

        logger.trace("Created model {} from {} based on {}", new Object[] { createdModel, erector, referenceModel });

        final T nonNullReferenceModel = referenceModel == null ? createdModel : referenceModel;
        erector.setReference(nonNullReferenceModel);

        if (withPolicies) {
            List<BlueprintPolicy> blueprintPolicies = this.getBlueprintPolicies().get(erector.getTarget());
            if (blueprintPolicies != null) {

                logger.debug("  Running Blueprint policies");

                for (BlueprintPolicy policy : blueprintPolicies) {
                    Map<ModelField, Set<Command>> modelFieldCommands = null;
                    try {
                        logger.info("    processing {}", policy);
                        modelFieldCommands = policy.process(this, erector, createdModel);

                    } catch (PolicyException e) {
                        throw new CreateModelException(e);
                    }

                    for (ModelField modelField : modelFieldCommands.keySet()) {
                        erector.addCommands(modelField, modelFieldCommands.get(modelField));
                    }
                }
            }
        }

        for (ModelField modelField : erector.getModelFields()) {

            logger.trace("ModelField {}", ReflectionToStringBuilder.toString(modelField));

            Object value = null;

            if (withPolicies) {
                List<FieldPolicy> policiesForSingleField = this.getFieldPolicies().get(modelField.getTarget());
                if (policiesForSingleField != null) {

                    logger.debug("  Running Field policies");

                    for (FieldPolicy policy : policiesForSingleField) {
                        try {
                            logger.info("    processing {} for {}", policy, modelField.getTarget());
                            Command command = policy.process(this, erector, modelField, createdModel);
                            if (command != null) {
                                erector.addCommand(modelField, command);
                            }
                        } catch (PolicyException e) {
                            new CreateModelException(e);
                        }
                    }
                }
            }

            if (erector.getCommands(modelField).size() > 0) {
                logger.debug("  ModelField commands: {}", erector.getCommands(modelField));
            }

            if (!erector.getCommands(modelField).contains(Command.SKIP_INJECTION)) {

                // Process DefaultField
                if (modelField instanceof DefaultField) {

                    DefaultField defaultField = (DefaultField) modelField;

                    if (!erector.getCommands(modelField).contains(Command.SKIP_REFERENCE_INJECTION)) {
                        try {
                            value = erector.getTemplate().get(nonNullReferenceModel, defaultField.getName());
                        } catch (BlueprintTemplateException e) {
                            throw new CreateModelException(e);
                        }
                    }

                    // If null or the field forces, use value set in blueprint, otherwise
                    // use the value of the reference model
                    if (!erector.getCommands(modelField).contains(Command.SKIP_BLUEPRINT_INJECTION) && (value == null || defaultField.isForce())) {
                        value = defaultField.getValue();
                    }

                    // If value is an instance of FieldCallBack, eval the callback and use the value
                    if (value != null && value instanceof Getable) {
                        Getable callBack = (Getable)value;
                        value = callBack.get(nonNullReferenceModel);
                    }

                    try {
                        createdModel = erector.getTemplate().set(createdModel, defaultField.getName(), value);
                    } catch (BlueprintTemplateException e) {
                        throw new CreateModelException(e);
                    }

                // Process MappedField
                } else if (modelField instanceof MappedField) {

                    MappedField mappedField = (MappedField) modelField;

                    if (!erector.getCommands(modelField).contains(Command.SKIP_REFERENCE_INJECTION)) {
                        try {
                            value = erector.getTemplate().get(nonNullReferenceModel, mappedField.getName());
                        } catch (BlueprintTemplateException e) {
                            throw new CreateModelException(e);
                        }
                    }

                    if (!erector.getCommands(modelField).contains(Command.SKIP_BLUEPRINT_INJECTION) && value == null && !mappedField.isNullable()) {
                        value = this.createModel(mappedField.getTarget());
                    }

                    try {
                        createdModel = erector.getTemplate().set(createdModel, mappedField.getName(), value);
                    } catch (BlueprintTemplateException e) {
                        throw new CreateModelException(e);
                    }

                // Process MappedListField
                } else if (modelField instanceof MappedListField) {

                    MappedListField listField = (MappedListField) modelField;

                    List modelList = null;
                    try {
                        value = (List) erector.getTemplate().construct(listField.getTargetList());
                    } catch (BlueprintTemplateException e) {
                        throw new CreateModelException(e);
                    }

                    if (!erector.getCommands(modelField).contains(Command.SKIP_INJECTION)) {
                        try {
                            modelList = (List) erector.getTemplate().get(nonNullReferenceModel, listField.getName());
                        } catch (BlueprintTemplateException e) {
                            throw new CreateModelException(e);
                        }
                    }

                    if (!erector.getCommands(modelField).contains(Command.SKIP_BLUEPRINT_INJECTION)) {
                        // Inject models into List If list is null or force is true or it is an empty list that is ignored
                        if ((modelList == null || listField.isForce()) || (modelList.size() == 0 && !listField.isIgnoreEmpty())) {
                            for (int x = 0; x < listField.getSize(); x++) {
                                ((List) value).add(this.createModel(listField.getTarget()));
                            }

                        } else {
                            for (Object object : modelList) {
                                ((List) value).add(this.createModel(object));
                            }
                        }
                    }

                    try {
                        createdModel = erector.getTemplate().set(createdModel, listField.getName(), value);
                    } catch (BlueprintTemplateException e) {
                        throw new CreateModelException(e);
                    }

                // Process MappedSetField
                } else if (modelField instanceof MappedSetField) {

                    MappedSetField setField = (MappedSetField) modelField;

                    try {
                        value = erector.getTemplate().construct(setField.getTargetSet());
                    } catch (BlueprintTemplateException e) {
                        throw new CreateModelException(e);
                    }

                    Set referenceModelSet = null;
                    if (!erector.getCommands(modelField).contains(Command.SKIP_INJECTION)) {
                        try {
                            referenceModelSet = (Set) erector.getTemplate().get(nonNullReferenceModel, setField.getName());
                        } catch (BlueprintTemplateException e) {
                            throw new CreateModelException(e);
                        }
                    }

                    if (!erector.getCommands(modelField).contains(Command.SKIP_BLUEPRINT_INJECTION)) {
                        // Inject models into Set If list is null or force is true or it is an empty set that is ignored
                        if ((referenceModelSet == null || setField.isForce()) || (referenceModelSet.size() == 0 && !setField.isIgnoreEmpty())) {
                            for (int x = 0; x < setField.getSize(); x++) {
                                ((Set) value).add(this.createModel(setField.getTarget()));
                            }
                        } else {
                            for (Object object : referenceModelSet) {
                                ((Set) value).add(this.createModel(object));
                            }
                        }
                    }

                    try {
                        createdModel = erector.getTemplate().set(createdModel, setField.getName(), value);
                    } catch (BlueprintTemplateException e) {
                        throw new CreateModelException(e);
                    }
                }
            }
        }

        List<Callback> afterCreateCallbacks = erector.getCallbacks("afterCreate");
        if ( afterCreateCallbacks != null ) {
            for (Callback callback : afterCreateCallbacks ) {
                if ( callback instanceof AfterCreateCallback ) {
                    createdModel = ((AfterCreateCallback<T>)callback).afterCreate(createdModel);
                } else {
                    // XXX: should this toss an exception?
                    logger.error("Invalid AfterCreateCallback registered for {}", referenceModel.getClass() );
                }
            }
        }

        return createdModel;
    }

    protected Object createNewInstance(Erector erector) throws BlueprintTemplateException {
        return erector.createNewInstance();
    }

    /**
     * Registered Blueprints
     *
     * @return List
     */
    public List<Object> getBlueprints() {
        return blueprints;
    }

    /**
     * Map of Class to their Erector.
     *
     * @return Map
     */
    public Map<Class, Erector> getErectors() {
        return erectors;
    }

    public Map<Class, List<BlueprintPolicy>> getBlueprintPolicies() {
        return blueprintPolicies;
    }

    public Map<Class, List<FieldPolicy>> getFieldPolicies() {
        return fieldPolicies;
    }

    /**
     * Get complete inherited list of Field for Class, with the exception
     * that ConstructorCallback fields are not inherited.
     *
     * @param clazz Class
     * @return Map
     */
    private Map<String,Field> getAllFields(Class clazz) {
        return this.getAllFields(clazz, false);
    }

    private Map<String,Field> getAllFields(Class clazz, boolean isParent) {
        Map<String,Field> fieldsMap = new HashMap<String,Field>();

        Class superClazz = clazz.getSuperclass();
        if(superClazz != null){
            fieldsMap.putAll( getAllFields(superClazz, true) );
        }

        for ( Field field: clazz.getDeclaredFields() ) {
            field.setAccessible(true);

            if (isParent) {
                // ConstructorCallbacks are not inherited
                if (!field.getType().equals( ConstructorCallback.class ) ) {
                    fieldsMap.put( field.getName(), field );
                }
            } else {
                fieldsMap.put( field.getName(), field );
            }
        }

        return fieldsMap;
    }
}
