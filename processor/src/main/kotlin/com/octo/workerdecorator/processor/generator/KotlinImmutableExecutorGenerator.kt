package com.octo.workerdecorator.processor.generator

import com.octo.kotlinelements.asKotlinTypeName
import com.octo.workerdecorator.annotation.WorkerDecorator
import com.octo.workerdecorator.processor.Generator
import com.octo.workerdecorator.processor.entity.Document
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.KModifier.OVERRIDE
import com.squareup.kotlinpoet.KModifier.PRIVATE
import java.util.concurrent.Executor

class KotlinImmutableExecutorGenerator : Generator {

    override fun generate(document: Document): String {
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
                .primaryConstructor(FunSpec.constructorBuilder()
                        .addParameter("decorated", decoratedType)
                        .addParameter("executor", Executor::class)
                        .build())
                .addProperty(PropertySpec.builder("decorated", decoratedType, PRIVATE).initializer("decorated").build())
                .addProperty(PropertySpec.builder("executor", Executor::class, PRIVATE).initializer("executor").build())
                .addFunctions(functions)
                .build()

        val source = FileSpec.builder(document.`package`, document.name)
                .addType(decoration)
                .addFunction(FunSpec.builder("decorate")
                        .receiver(WorkerDecorator::class)
                        .returns(decoratedType)
                        .addParameter("implementation", decoratedType)
                        .addParameter("executor", Executor::class)
                        .addStatement("return ${document.name}(implementation, executor)")
                        .build())
                .build()

        return source.toString()
    }
}