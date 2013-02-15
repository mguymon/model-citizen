package com.tobedevoured.modelcitizen.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Callback when ModelFactory creates a new instance of the Model. Used in
 * conjuction with (@link ConstructorCallback}
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NewInstance {

}
