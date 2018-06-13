package com.octo.kotlinelements

import org.jetbrains.annotations.NotNull
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeKind.*

/**
 * Returns `true` if the given [VariableElement] should be considered a Kotlin optional type.
 * Java primitives (see [isPrimitive]) are not considered optionals.
 */
fun VariableElement.isOptional(): Boolean = !isPrimitive() && this.getAnnotation(NotNull::class.java) == null

/**
 * Returns `true` if the given [VariableElement] represents a Java primitive type
 */
fun VariableElement.isPrimitive(): Boolean =
    listOf(BOOLEAN, BYTE, SHORT, INT, LONG, CHAR, FLOAT, DOUBLE).contains(this.asType().kind)