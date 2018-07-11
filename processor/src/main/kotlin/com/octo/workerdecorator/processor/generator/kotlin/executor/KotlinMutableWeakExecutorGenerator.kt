package com.octo.workerdecorator.processor.generator.kotlin.executor

import com.octo.kotlinelements.asKotlinTypeName
import com.octo.workerdecorator.WorkerDecoration
import com.octo.workerdecorator.processor.Generator
import com.octo.workerdecorator.processor.entity.DecorationDocument
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.KModifier.OVERRIDE
import com.squareup.kotlinpoet.KModifier.PRIVATE
import java.lang.ref.WeakReference
import java.util.concurrent.Executor

class KotlinMutableWeakExecutorGenerator : Generator {

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
                .addStatement("executor.execute { decorated?.get()?.${it.name}($bodyParameters) }")
                .build()
        }.asIterable()


        val workerDecorationType = ParameterizedTypeName.get(WorkerDecoration::class.asClassName(), decoratedType)
        val weakReferenceType = WeakReference::class.asClassName()
        val weakDecorationType = ParameterizedTypeName.get(weakReferenceType, decoratedType.asNullable())

        val decoration = TypeSpec.classBuilder(document.name)
            .addSuperinterface(decoratedType)
            .addSuperinterface(workerDecorationType)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter("executor", Executor::class)
                    .build()
            )
            .addProperty(
                PropertySpec
                    .builder("executor", Executor::class, PRIVATE)
                    .initializer("executor")
                    .build()
            )
            .addProperty(
                PropertySpec
                    .varBuilder("decorated", weakDecorationType.asNullable(), PRIVATE)
                    .initializer("null")
                    .build()
            )
            .addFunctions(functions)
            .addFunction(
                FunSpec.builder(WorkerDecoration<Any>::setDecorated.name)
                    .addModifiers(OVERRIDE)
                    .addParameter("decorated", decoratedType.asNullable())
                    .addStatement("this.decorated = WeakReference(decorated)")
                    .build()
            )
            .addFunction(
                FunSpec.builder(WorkerDecoration<Any>::asType.name)
                    .addModifiers(OVERRIDE)
                    .returns(decoratedType)
                    .addStatement("return this")
                    .build()
            )
            .build()

        val source = FileSpec.get(document.`package`, decoration)
        return source.toString()
    }
}