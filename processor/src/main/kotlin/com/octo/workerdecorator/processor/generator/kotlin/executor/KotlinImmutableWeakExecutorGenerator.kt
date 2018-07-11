package com.octo.workerdecorator.processor.generator.kotlin.executor

import com.octo.kotlinelements.asKotlinTypeName
import com.octo.workerdecorator.processor.Generator
import com.octo.workerdecorator.processor.entity.DecorationDocument
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.KModifier.OVERRIDE
import com.squareup.kotlinpoet.KModifier.PRIVATE
import java.lang.ref.WeakReference
import java.util.concurrent.Executor

class KotlinImmutableWeakExecutorGenerator : Generator {

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
                .addStatement("executor.execute { decorated.get()?.${it.name}($bodyParameters) }")
                .build()
        }.asIterable()

        val weakReferenceType = WeakReference::class.asClassName()
        val weakDecorationType = ParameterizedTypeName.get(weakReferenceType, decoratedType)

        val decoration = TypeSpec.classBuilder(document.name)
            .addSuperinterface(decoratedType)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter("decorated", decoratedType)
                    .addParameter("executor", Executor::class)
                    .build()
            )
            .addProperty(
                PropertySpec.builder(
                    "decorated",
                    weakDecorationType,
                    PRIVATE
                )
                    .initializer("%T(decorated)", weakReferenceType)
                    .build()
            )
            .addProperty(
                PropertySpec.builder("executor", Executor::class, PRIVATE)
                    .initializer("executor")
                    .build()
            )
            .addFunctions(functions)
            .build()

        val source = FileSpec.get(document.`package`, decoration)
        return source.toString()
    }
}