package com.octo.workerdecorator.processor.generator

import com.octo.workerdecorator.annotation.WorkerDecoration
import com.octo.workerdecorator.processor.AggregateGenerator
import com.octo.workerdecorator.processor.entity.AggregateDocument
import com.octo.workerdecorator.processor.entity.Document
import com.octo.workerdecorator.processor.entity.Mutability.IMMUTABLE
import com.octo.workerdecorator.processor.entity.Mutability.MUTABLE
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.KModifier.INLINE
import java.util.concurrent.Executor

class KotlinExecutorAggregatorGenerator : AggregateGenerator {

    override fun generate(aggregator: Document, documents: List<AggregateDocument>): String {

        val immutableHelperFunctions = documents
            .filter { it.mutability == IMMUTABLE }
            .map {

                val decoratedType = it.typeMirror.asTypeName()
                val decoration = ClassName(it.`package`, it.name)

                FunSpec.builder("decorate")
                    .returns(decoratedType)
                    .addParameter("implementation", decoratedType)
                    .addParameter("executor", Executor::class)
                    .addStatement("return %T(implementation, executor)", decoration)
                    .build()
            }

        val parameterizedTypeName = ParameterizedTypeName.get(
            WorkerDecoration::class.asClassName(),
            TypeVariableName.invoke("DECORATED")
        )
        val mutableHelperFunction = FunSpec.builder("decorate")
            .addAnnotation(
                AnnotationSpec.builder(Suppress::class)
                    .addMember("%S", "UNCHECKED_CAST")
                    .build()
            )
            .addModifiers(INLINE)
            .addTypeVariable(
                TypeVariableName.invoke("DECORATED")
                    .reified(true)
            )
            .addParameter("executor", Executor::class)
            .returns(parameterizedTypeName)
            .addCode(
                CodeBlock.builder()
                    .beginControlFlow("return when(DECORATED::class)")
                    .apply {
                        documents
                            .filter { it.mutability == MUTABLE }
                            .forEach {
                                val decoration = ClassName(it.`package`, it.name)
                                addStatement(
                                    "%T::class ->%W%T(executor) as %T",
                                    it.typeMirror,
                                    decoration,
                                    parameterizedTypeName
                                )
                            }
                    }
                    .addStatement(
                        "else ->%Wthrow %T(%S)",
                        RuntimeException::class.asClassName(),
                        "Impossible to find a decoration for \${DECORATED::class.java.name}"
                    )
                    .endControlFlow()
                    .build()
            )
            .build()

        val source = FileSpec.get(
            aggregator.`package`, TypeSpec.objectBuilder(aggregator.name)
                .addFunction(mutableHelperFunction)
                .addFunctions(immutableHelperFunctions)
                .build()
        )

        return source.toString()
    }
}