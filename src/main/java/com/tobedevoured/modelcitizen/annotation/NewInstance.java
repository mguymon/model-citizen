package com.tobedevoured.modelcitizen.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Callback when ModelFactory creates a new instance of the Model. Used in
 * conjunction with (@link ConstructorCallback}
 * @deprecated No longer required, {@link ConstructorCallback} is automatically detected
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface NewInstance {

}
