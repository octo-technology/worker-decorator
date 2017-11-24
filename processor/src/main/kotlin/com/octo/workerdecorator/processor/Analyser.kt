package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Document
import com.octo.workerdecorator.processor.entity.Method
import com.octo.workerdecorator.processor.entity.Parameter
import org.jetbrains.annotations.NotNull
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeKind.*
import javax.lang.model.util.Elements

/**
 * Class responsible for creating a [Document] entity from a [TypeElement] input
 *
 * All the members (possibly inherited) from the given [TypeElement] are browsed.
 * Only the methods are kept.
 * The methods declared in [Any] are discarded.
 *
 * A [Document] object is returned, containing the analysed file package name,
 * decorated name, and a representation of the methods to be overridden.
 */
class Analyser(private val elements: Elements) {

    fun analyse(input: TypeElement): Document {
        val methods = elements.getAllMembers(input)
                .filter { it.kind == ElementKind.METHOD }
                .map { it as ExecutableElement }
                .filter { it.enclosingElement.simpleName.toString() != Any::class.java.simpleName }
                .map { Method(it.simpleName.toString(), makeParameterList(it.parameters)) }

        val originalQualifiedName = input.qualifiedName.toString()
        val originalPackage = originalQualifiedName.split(".").dropLast(1).joinToString(".")
        val originalName = input.simpleName
        val isWrittenInKotlin = input.annotationMirrors.map { it.annotationType.toString() }.contains("kotlin.Metadata")

        return Document(originalPackage, "${originalName}Decorated", methods, input.asType(), isWrittenInKotlin)
    }

    private fun makeParameterList(input: List<VariableElement>): List<Parameter> {
        return input.map {
            val name = it.simpleName.toString()
            val isOptional = !(it.isPrimitive() || it.hasNotNullAnnotation())
            Parameter(name, it.asType(), isOptional)
        }
    }
}

fun VariableElement.hasNotNullAnnotation(): Boolean
        = this.getAnnotation(NotNull::class.java) != null

fun VariableElement.isPrimitive(): Boolean
        = listOf(BOOLEAN, BYTE, SHORT, INT, LONG, CHAR, FLOAT, DOUBLE).contains(this.asType().kind)