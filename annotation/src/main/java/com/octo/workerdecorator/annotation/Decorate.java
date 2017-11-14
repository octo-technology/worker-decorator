package com.octo.workerdecorator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells the worker-decorator processor that it should generate a class decorating the annotated interface<br/>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Decorate {
    /**
     * TODO
     */
    boolean decoratedObjectIsMutable() default false;
}
