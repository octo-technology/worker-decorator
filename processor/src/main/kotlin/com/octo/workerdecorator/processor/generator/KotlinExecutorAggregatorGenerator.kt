package com.octo.workerdecorator.processor.generator

import com.octo.workerdecorator.annotation.WorkerDecoration
import com.octo.workerdecorator.processor.AggregateGenerator
import com.octo.workerdecorator.processor.entity.AggregateDocument
import com.octo.workerdecorator.processor.entity.Document
import com.octo.workerdecorator.processor.entity.Mutability
import com.squareup.kotlinpoet.*
import java.util.concurrent.Executor

class KotlinExecutorAggregatorGenerator : AggregateGenerator {

    override fun generate(aggregator: Document, documents: List<AggregateDocument>): String {

        val functions = documents.map {

            val decoratedType = it.typeMirror.asTypeName()
            val decoration = ClassName(it.`package`, it.name)

            if (it.mutability == Mutability.IMMUTABLE) {
                FunSpec.builder("decorate")
                    .returns(decoratedType)
                    .addParameter("implementation", decoratedType)
                    .addParameter("executor", Executor::class)
                    .addStatement("return %T(implementation, executor)", decoration)
                    .build()

            } else {
                val workerDecorationType =
                    ParameterizedTypeName.get(WorkerDecoration::class.asClassName(), decoratedType)

                FunSpec.builder("decorate")
                    .returns(workerDecorationType)
                    .addParameter("executor", Executor::class)
                    .addStatement("return %T(executor)", decoration)
                    .build()
            }
        }


        val source = FileSpec.get(
            aggregator.`package`, TypeSpec.objectBuilder(aggregator.name)
                .addFunctions(functions)
                .build()
        )

        return source.toString()
    }
}