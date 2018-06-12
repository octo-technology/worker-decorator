package com.octo.workerdecorator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells the worker-decorator processor that it should generate a class decorating the annotated interface
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Decorate {
    /**
     * @return Specify if it should be possible to modify the instance given to the decoration
     */
    boolean mutable() default false;
}
