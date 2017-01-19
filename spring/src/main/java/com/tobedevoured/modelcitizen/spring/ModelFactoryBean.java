package com.tobedevoured.modelcitizen.spring;

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

import com.tobedevoured.modelcitizen.Erector;
import com.tobedevoured.modelcitizen.ModelFactory;
import com.tobedevoured.modelcitizen.RegisterBlueprintException;
import com.tobedevoured.modelcitizen.annotation.Blueprint;
import com.tobedevoured.modelcitizen.annotation.NotSet;
import com.tobedevoured.modelcitizen.spring.annotation.SpringBlueprint;
import com.tobedevoured.modelcitizen.template.BlueprintTemplateException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * {@link ModelFactory} that is Spring aware
 */
public class ModelFactoryBean extends ModelFactory implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(ModelFactoryBean.class);
    private ApplicationContext applicationContext;

    @Autowired
    private AutowireCapableBeanFactory beanFactory;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Create new instance of model before blueprint values are set. Autowire them from Spring Context if they have the
     * SpringBlueprint annotation
     *
     * @param erector Erector
     * @return Object
     * @throws BlueprintTemplateException failed to apply blueprint
     */
    @Override
    protected Object createNewInstance(Erector erector) throws BlueprintTemplateException {
        SpringBlueprint springBlueprint = erector.getBlueprint().getClass().getAnnotation(SpringBlueprint.class);
        if ( springBlueprint != null && springBlueprint.bean() ) {
            Class beanClass = springBlueprint.beanClass();

            if ( beanClass.equals( NotSet.class) ) {
                beanClass = erector.getTarget();
            }

            try {
                if ( StringUtils.isNotBlank( springBlueprint.beanName() ) ) {
                    logger.debug( "Retrieving model from Spring [{},{}]", springBlueprint.beanName(), beanClass );
                    return applicationContext.getBean( springBlueprint.beanName(), beanClass );
                } else {
                    logger.debug( "Retrieving model from Spring [{}]", beanClass );
                    return applicationContext.getBean( beanClass );
                }
            } catch( NoSuchBeanDefinitionException e ) {
                // Model not directly registered with Spring, autowire it manually.
                Object instance = super.createNewInstance(erector);
                beanFactory.autowireBean( instance );
                return instance;
            }
        } else {
            return super.createNewInstance(erector);
        }

    }

    /**
     * Register Blueprints, autowire them from Spring Context if they have the SpringBlueprint annotation
     *
     * @param blueprint Blueprint
     * @throws RegisterBlueprintException failed to register blueprint
     */
    @Override
    public void registerBlueprint(Object blueprint) throws RegisterBlueprintException {
        SpringBlueprint springBlueprint = blueprint.getClass().getAnnotation(SpringBlueprint.class);
        if ( springBlueprint != null && springBlueprint.autowire() ) {
            logger.debug( "Autowiring blueprint {}", blueprint );
            beanFactory.autowireBean( blueprint );
        }
        super.registerBlueprint(blueprint);
    }
}
