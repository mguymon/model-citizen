# Modle Citizen

Model Citizen is an annotation based model factory for Java.

https://github.com/mguymon/model-citizen

A Model is mapped by a _@Blueprint_ using annotated fields. Blueprints contain 
default values and references to other @Blueprinted models. The ModelFactory can 
create Models based on registered Blueprints. A Model already created can be
passed into the ModelFactory as a Reference Model, which will be used as the basis
for the new Model. 

Presently only supports [template for JavaBean Models](https://github.com/mguymon/model-citizen/blob/master/src/main/java/com/tobedevoured/modelcitizen/template/JavaBeanTemplate.java). For Models defaults to work, they must follow the [JavaBean](http://en.wikibooks.org/wiki/Java_Programming/Java_Beans) getter. and setters.

Inspired by [FactoryGirl](https://github.com/thoughtbot/factory_girl)

## Install

    <dependency>
      <groupId>com.tobedevoured</groupId>
      <artifactId>modelcitizen</artifactId>
      <version>0.3.0</version>
    </dependency>
    
May have to add the Sonatype Repo if the sync to Maven Central is slow.

    <repositories>
      <repository>
        <id>oss.sonatype.org</id>
        <name>Sonatype Repository</name>
        <url>https://oss.sonatype.org/content/groups/public</url>
      </repository>
    </repositories>


## Blueprint 

A blueprint is a Class anototated with _@Blueprint( Class )_ and contains annotated fields. Everything else is ignored by the _ModelFactory_.

### Field Annotations

* **@Default**: The default value for the field.
* **@Mapped**: The value is mapped to another @Blueprint, default is the blueprint for
               matching field's Class. Mapped class can be set by the _target_ param. 
* **@MappedList**: Creates a List of Models mapped to another blueprint. The Mapped
                   class of the List is set by the  _target_ param and the number of
                   Models created is set by the _size_ param, the default is 1.
* **@MappedSet**: Creates a Set of Models mapped to another blueprint. The Mapped
                  class of the Set is set by the  _target_ param and the number of
                  Models created is set by the _size_ param, the default is 1.
* **@NewInstance**: Set on a _ConstructCallback_ field. The callback is used to create new
                    instances of the model by the _ModelFactory_
* **@Nullable**: Specifies this field can be null and not to set a value.

## ModelFactory

Creates new instances of Models based on registered Blueprints. It is possible to 
[registered Blueprints by package](https://github.com/mguymon/model-citizen/wiki/Register-By-Package).

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


## Future Features

* Spring support: Model instances can be pased by Spring
* Multiple Blueprints: Allow multiple Blueprints for the same class

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