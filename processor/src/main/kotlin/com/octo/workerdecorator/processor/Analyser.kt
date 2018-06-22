package com.octo.workerdecorator.processor

import com.octo.kotlinelements.isOptional
import com.octo.kotlinelements.isProducedByKotlin
import com.octo.workerdecorator.annotation.Decorate
import com.octo.workerdecorator.processor.entity.AggregateDocument
import com.octo.workerdecorator.processor.entity.DecorationDocument
import com.octo.workerdecorator.processor.entity.Method
import com.octo.workerdecorator.processor.entity.Mutability.IMMUTABLE
import com.octo.workerdecorator.processor.entity.Mutability.MUTABLE
import com.octo.workerdecorator.processor.entity.Parameter
import com.octo.workerdecorator.processor.entity.ReferenceStrength.STRONG
import com.octo.workerdecorator.processor.entity.ReferenceStrength.WEAK
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.util.Elements

/**
 * Class responsible for creating a [DecorationDocument] entity from a [TypeElement] input
 *
 * All the members (possibly inherited) from the given [TypeElement] are browsed.
 * Only the methods are kept.
 * The methods declared in [Any] are discarded.
 *
 * A [DecorationDocument] object is returned, containing the analysed file package name,
 * decorated name, and a representation of the methods to be overridden.
 */
class Analyser(private val elements: Elements) {

    fun analyse(input: TypeElement): DecorationDocument {
        val methods = elements.getAllMembers(input)
            .filter { it.kind == ElementKind.METHOD }
            .map { it as ExecutableElement }
            .filter { it.enclosingElement.simpleName.toString() != Any::class.java.simpleName }
            .map { Method(it.simpleName.toString(), makeParameterList(it.parameters)) }

        val originalPackage = extractPackage(input)
        val originalName = input.simpleName
        val isWrittenInKotlin = input.isProducedByKotlin()

        return DecorationDocument(
            originalPackage,
            "${originalName}Decorated",
            methods,
            input.asType(),
            isWrittenInKotlin
        )
    }

    fun analyse(input: List<Pair<TypeElement, Decorate>>): List<AggregateDocument> {
        return input.map {

            val element = it.first
            val originalPackage = extractPackage(element)
            val originalName = element.simpleName

            val mutability = if (it.second.mutable) MUTABLE else IMMUTABLE
            val strength = if (it.second.weak) WEAK else STRONG

            AggregateDocument(
                originalPackage,
                "${originalName}Decorated",
                element.asType(),
                mutability,
                strength
            )
        }
    }

    private fun extractPackage(input: TypeElement) =
        input.qualifiedName.toString().split(".").dropLast(1).joinToString(".")

    private fun makeParameterList(input: List<VariableElement>): List<Parameter> =
        input.map { Parameter(it.simpleName.toString(), it.asType(), it.isOptional()) }
}