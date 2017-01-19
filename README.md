# Model Citizen

[![Build Status](https://travis-ci.org/mguymon/model-citizen.png?branch=master)](https://travis-ci.org/mguymon/model-citizen)

Model Citizen is an annotation based model factory for Java, inspired by [FactoryGirl](https://github.com/thoughtbot/factory_girl)

A Model is mapped by a _@Blueprint_ using annotated fields. Blueprints contain 
default values and references to other @Blueprinted models. The _ModelFactory_ can 
create models based on registered blueprints. A model already created can be
passed into the _ModelFactory_ as a reference model, which will be used as the basis
for the new Model. 


<https://github.com/mguymon/model-citizen>

[JavaDoc](http://mguymon.github.com/model-citizen/apidocs/index.html)


## Install

    <dependency>
      <groupId>com.tobedevoured.modelcitizen</groupId>
      <artifactId>core</artifactId>
      <version>0.8.2</version>
    </dependency>

## Blueprint 

A blueprint is a Class annotated with _@Blueprint( Class )_ and contains annotated fields. Everything else is ignored by the _ModelFactory_.
Model Citizen [own blueprints](https://github.com/mguymon/model-citizen/tree/master/core/src/test/java/com/tobedevoured/modelcitizen/blueprint) are a great example of how they work.

**@Blueprint**: Class annotation
   * _value_: [Class] The target model class for this blueprint.
   * _template_: [Class] The [template](https://github.com/mguymon/model-citizen/blob/master/core/src/main/java/com/tobedevoured/modelcitizen/template/BlueprintTemplate.java) use to construct and inject the model. Defaults to [JavaBeanTemplate](https://github.com/mguymon/model-citizen/blob/master/core/src/main/java/com/tobedevoured/modelcitizen/template/JavaBeanTemplate.java)


### Field Annotations
**@Default**: The default value for the field.
   * _force_:  [boolean] Force the value of the Default to always be set, even if the target field already has a value. Default is false. This is useful for overriding primatives or Collections.

**@Mapped**: The value is mapped to another @Blueprint, default is the blueprint for
               matching field's Class. Mapped class can be set by the _target_ param. 

**@MappedList**: Creates a List of Models mapped to another blueprint.
  * _size_: [int] Number of Models to be created by the ModelFactory and added to List, defaults to 1.
  * _target_: [Class] The target Blueprint Class used to create Models
  * _targetList_: [Class] The List created, defaults to ArrayList
  * _ignoreEmpty_: [boolean] If true, do not create Models for an empty Set. Defaults to true.
  * _force_: [boolean] Force the value of the MappedList to always be set, even if the target field already has a value. Default is false.
  
**@MappedSet**: Creates a Set of Models mapped to another blueprint.
  * _size_: [int] Number of Models to be created by the ModelFactory and added to Set, defaults to 1.
  * _target_: [Class] The target Blueprint Class used to create Models
  * _targetSet_: [Class] The Set created, defaults to HashSet
  * _ignoreEmpty_: [boolean] If true, do not create Models for an empty Set. Defaults to true.
  * _force_:  [boolean] Force the value of the MappedSet to always be set, even if the target field already has a value. Default is false.
                    
**@Nullable**: Specifies this field can be null and not to set a value by the ModelFactory.

### Callbacks

Callback fields can be used to inject values at the various stages of the model creation lifecycle:

**ConstructorCallback**: Use when constructing a new instance of the model, the model return by the callback is used by the ModelFactory. ([example blueprint](https://github.com/mguymon/model-citizen/blob/master/core/src/test/java/com/tobedevoured/modelcitizen/blueprint/SpareTireBlueprint.java)).

**FieldCallback**: Injects the return of the callback for the annotated field value ([example blueprint](https://github.com/mguymon/model-citizen/blob/master/core/src/test/java/com/tobedevoured/modelcitizen/blueprint/UserBlueprint.java)).

**AfterCreateCallback**: Executed after the model has been constructed and all the fields are set ([example blueprint](https://github.com/mguymon/model-citizen/blob/master/core/src/test/java/com/tobedevoured/modelcitizen/blueprint/SpareTireBlueprint.java)).

### Inheritance

A Blueprint will inherit the fields of its parent, except for `ConstructorCallback`.

## Model

Presently only supports [template for JavaBean Models](https://github.com/mguymon/model-citizen/blob/master/core/src/main/java/com/tobedevoured/modelcitizen/template/JavaBeanTemplate.java). 
For annotations to work with the template, the model must follow the [JavaBean](http://en.wikibooks.org/wiki/Java_Programming/Java_Beans) getter and setters
for fields.

### Working with primitives

[Primitive fields are intialized as zero](http://docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.12.5) and 
will never be null. This will cause the _ModelFactory_ to think that a value has already been assigned to the model and 
not assign one from the blueprint. To work around this, use the `force=true` in the blueprint to force that value is always assigned from the blueprint, for example:

    @Blueprint(Car.class)
    public class CarBlueprint {
        
    @Default(force=true)
    float mileage = 100.1f;

## ModelFactory

Creates new instances of Models based on registered Blueprints. It is possible to 
[register Blueprints by package](https://github.com/mguymon/model-citizen/wiki/Register-By-Package).

A new instance is always constructed, unless specified by a 
[Policy](https://github.com/mguymon/model-citizen/wiki/Policy).

Example of creating a new _ModelFactory_ and registering the _CarBlueprint_

    ModelFactory modelFactory = new ModelFactory();
    modelFactory.registerBlueprint( CarBlueprint.class );
    
A Model with a registered Blueprint can then me created by Class:

    modelFactory.createModel(Car.class);
  
or by passing a Model directly with override values: 

    Car car = new Car();
    car.setMake( "Truck" );
    
    # create a new Model using blueprint defaults, but overriding the Make to be Truck.
    car = modelFactory.createModel(car); 

## A Simple Example

### Creating a model

    ModelFactory modelFactory = new ModelFactory();
    modelFactory.registerBlueprint( CarBlueprint.class );
    Car car = modelFactory.createModel(Car.class);

### Creating a model overriding defaults

    Car car = new Car()
    car.setMake( "Caddie" );
    car = modelFactory.createModel(car);

### Car model's Blueprint

    @Blueprint(Car.class)
    public class CarBlueprint {
        
        @Default
        String make = "car make";
            
        @Default
        String manufacturer = "car manufacturer";
            
        @Default
        Integer mileage = 100;
            
        @Default
        Map status = new HashMap();
    }
    
### The Car Model

    public class Car {
        private String make;
        private String manufacturer;
        private Integer mileage;
        private Map status;
    
        public String getMake() {
            return make;
        }
    
        public void setMake(String make) {
            this.make = make;
        }
    
        public String getManufacturer() {
            return manufacturer;
        }
    
        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }
    
        public Integer getMileage() {
            return mileage;
        }
    
        public void setMileage(Integer mileage) {
            this.milage = mileage;
        }
    
        public Map getStatus() {
            return status;
        }
    
        public void setStatus(Map status) {
            this.status = status;
        }
    }

[Wiki](https://github.com/mguymon/model-citizen/wiki) of examples that
includes [Callbacks](https://github.com/mguymon/model-citizen/wiki/Callback-Example), [Policies](https://github.com/mguymon/model-citizen/wiki/Policy), and [Package scanning for Blueprints](https://github.com/mguymon/model-citizen/wiki/Register-By-Package).

## Spring Support

There is an optional jar that provides additional support for Spring:


    <dependency>
      <groupId>com.tobedevoured.modelcitizen</groupId>
      <artifactId>spring</artifactId>
      <version>0.8.1</version>
    </dependency>
    
### Avoiding Spring jar collisions

ModelFactory should work with Spring 3.x, so you can easily exclude ModelFactory's Spring depedency and use the existing one in your pom.

    <dependency>
      <groupId>com.tobedevoured.modelcitizen</groupId>
      <artifactId>spring</artifactId>
      <version>0.8.1</version>
      <exclusions>
        <exclusion>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
        </exclusion>
      </exclusions>
    </dependency>
    
### What does this give me?

This provides a new class [ModelFactoryBean](https://github.com/mguymon/model-citizen/blob/master/spring/src/main/java/com/tobedevoured/modelcitizen/spring/ModelFactoryBean.java) and annotation [@SpringBlueprint](https://github.com/mguymon/model-citizen/blob/master/spring/src/main/java/com/tobedevoured/modelcitizen/spring/annotation/SpringBlueprint.java). When the ModelFactoryBean is registered with Spring, it allows the `@SpringBlueprint` and models created by the ModelFactoryBean to be injected by Spring.

#### Example ModelFactoryBean

    <bean id="modelFactory" class="com.tobedevoured.modelcitizen.spring.ModelFactoryBean" >
        <property name="registerBlueprintsByPackage" value="com.tobedevoured.modelcitizen" />
    </bean>

#### Example @SpringBlueprint with Spring support

    @Blueprint(SportsCar.class)
    @SpringBlueprint
    public class SportsCarBlueprint {

        @Autowired
        SportsCarRepository sportsCarRepository;
        
        @Default
        Integer topSpeed = 100;
    }
    
    
## License

Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with this
work for additional information regarding copyright ownership.  The ASF
licenses this file to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
