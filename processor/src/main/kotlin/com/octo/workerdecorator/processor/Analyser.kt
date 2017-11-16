package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Document
import com.octo.workerdecorator.processor.entity.Method
import com.octo.workerdecorator.processor.entity.Parameter
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.util.Elements

class Analyser(private val elements: Elements) {

    fun analyse(input: TypeElement): Document {
        val methods = elements.getAllMembers(input)
                .filter { it.kind == ElementKind.METHOD }
                .map { it as ExecutableElement }
                .filter { it.enclosingElement.simpleName.toString() != Any::class.java.simpleName }
                .map { Method(it.simpleName.toString(), makeParameterList(it.parameters), it) }

        val originalQualifiedName = input.qualifiedName.toString()
        val originalPackage = originalQualifiedName.split(".").dropLast(1).joinToString(".")
        val originalName = input.simpleName
        return Document(originalPackage, "${originalName}Decorated", methods, input.asType())
    }

    private fun makeParameterList(input: List<VariableElement>): List<Parameter>
            = input.map { Parameter(it.simpleName.toString(), it.asType()) }
}