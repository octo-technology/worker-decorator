package com.octo.workerdecorator.processor.generator

import com.octo.kotlinelements.asKotlinTypeName
import com.octo.workerdecorator.processor.Generator
import com.octo.workerdecorator.processor.entity.DecorationDocument
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.KModifier.OVERRIDE
import com.squareup.kotlinpoet.KModifier.PRIVATE
import java.util.concurrent.Executor

class KotlinImmutableExecutorGenerator : Generator {

    override fun generate(document: DecorationDocument): String {
        val decoratedType = document.typeMirror.asTypeName()

        val functions = document.methods.map {

            val specParameters = it.parameters.map {
                var typeName = it.typeMirror.asTypeName()
                if (document.interfaceIsInKotlin) {
                    typeName = typeName.asKotlinTypeName()
                }
                typeName = if (it.isOptional) {
                    typeName.asNullable()
                } else {
                    typeName.asNonNullable()
                }

                ParameterSpec.builder(it.name, typeName).build()
            }
            val bodyParameters = it.parameters.joinToString(", ") { it.name }

            FunSpec.builder(it.name)
                .addModifiers(OVERRIDE)
                .addParameters(specParameters)
                .addStatement("executor.execute { decorated.${it.name}($bodyParameters) }")
                .build()
        }.asIterable()

        val decoration = TypeSpec.classBuilder(document.name)
            .addSuperinterface(decoratedType)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter("decorated", decoratedType)
                    .addParameter("executor", Executor::class)
                    .build()
            )
            .addProperty(PropertySpec.builder("decorated", decoratedType, PRIVATE).initializer("decorated").build())
            .addProperty(PropertySpec.builder("executor", Executor::class, PRIVATE).initializer("executor").build())
            .addFunctions(functions)
            .build()

        val source = FileSpec.get(document.`package`, decoration)
        return source.toString()
    }
}