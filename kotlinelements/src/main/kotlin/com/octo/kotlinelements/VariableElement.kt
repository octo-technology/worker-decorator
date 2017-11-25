package com.octo.kotlinelements

import org.jetbrains.annotations.NotNull
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeKind.*

fun VariableElement.isOptional(): Boolean
        = !isPrimitive() && this.getAnnotation(NotNull::class.java) == null

fun VariableElement.isPrimitive(): Boolean
        = listOf(BOOLEAN, BYTE, SHORT, INT, LONG, CHAR, FLOAT, DOUBLE).contains(this.asType().kind)