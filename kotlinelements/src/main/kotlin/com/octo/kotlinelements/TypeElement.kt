package com.octo.kotlinelements

import javax.lang.model.element.TypeElement

/**
 * Returns `true` if the given [TypeElement] comes from a compiled kotlin
 * source code (if it is annotated with the kotlin [Metadata] annotation)
 */
fun TypeElement.isProducedByKotlin(): Boolean
        = this.annotationMirrors.map { it.annotationType.toString() }.contains("kotlin.Metadata")