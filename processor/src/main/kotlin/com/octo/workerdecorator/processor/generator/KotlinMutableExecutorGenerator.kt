package com.octo.workerdecorator.processor.generator

import com.octo.workerdecorator.processor.Generator
import com.octo.workerdecorator.processor.entity.Document
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.KModifier.*
import java.util.concurrent.Executor

class KotlinMutableExecutorGenerator : Generator {

    override fun generate(document: Document): String {
        val decoratedType = document.typeMirror.asTypeName()

        val functions = document.methods.map {

            val specParameters = it.parameters.map {
                val typeName = it.typeMirror.asTypeName()
                val optTypeName = if (it.isOptional) typeName.asNullable() else typeName.asNonNullable()
                ParameterSpec.builder(it.name, optTypeName).build()
            }
            val bodyParameters = it.parameters.joinToString(", ") { it.name }

            FunSpec.builder(it.name)
                    .addModifiers(OVERRIDE)
                    .addParameters(specParameters)
                    .addStatement("executor.execute { decorated?.${it.name}($bodyParameters) }")
                    .build()
        }.asIterable()

        val source = FileSpec.get(document.`package`, TypeSpec.classBuilder(document.name)
                .addSuperinterface(decoratedType)
                .primaryConstructor(FunSpec.constructorBuilder()
                        .addParameter("executor", Executor::class)
                        .build())
                .addProperty(PropertySpec
                        .builder("executor", Executor::class, PRIVATE)
                        .initializer("executor")
                        .build())
                .addProperty(PropertySpec
                        .varBuilder("decorated", decoratedType.asNullable(), PUBLIC)
                        .initializer("null")
                        .build())
                .addFunctions(functions)
                .build())

        return source.toString()
    }
}