package com.octo.workerdecorator.processor.generator

import com.octo.workerdecorator.processor.Generator
import com.octo.workerdecorator.processor.entity.Document
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.KModifier.PRIVATE
import java.util.concurrent.Executor

class KotlinImmutableExecutorGenerator : Generator {

    override fun generate(document: Document): String {

        val decoratedType = document.originalTypeMirror.asTypeName()

        val functions = document.methods.map {

            val args = it.parameters.map { it.name }.joinToString(", ")

            FunSpec.overriding(it.executableElement)
                    .addStatement("executor.execute { decorated.${it.name}($args) }")
                    .build()
        }.asIterable()

        val source = FileSpec.get(document.`package`, TypeSpec.classBuilder(document.name)
                .addSuperinterface(decoratedType)
                .primaryConstructor(FunSpec.constructorBuilder()
                        .addParameter("executor", Executor::class)
                        .addParameter("decorated", decoratedType)
                        .build())
                .addProperty(PropertySpec.builder("executor", Executor::class, PRIVATE).initializer("executor").build())
                .addProperty(PropertySpec.builder("decorated", decoratedType, PRIVATE).initializer("decorated").build())
                .addFunctions(functions)
                .build())

        return source.toString()
    }
}