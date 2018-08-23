package com.octo.workerdecorator.processor.generator.kotlin

import com.octo.workerdecorator.processor.HelperGenerator
import com.octo.workerdecorator.processor.entity.Document
import com.octo.workerdecorator.processor.entity.HelperDocument
import com.octo.workerdecorator.processor.entity.Mutability
import com.octo.workerdecorator.processor.entity.ReferenceStrength
import com.squareup.kotlinpoet.*
import kotlin.coroutines.experimental.CoroutineContext

class KotlinCoroutinesHelperGenerator : HelperGenerator {
    override fun generate(aggregator: Document, documents: List<HelperDocument>): String {
        val immutableHelperFunctions = documents
                .filter { it.mutability == Mutability.IMMUTABLE }
                .filter { it.reference == ReferenceStrength.STRONG }
                .map {
                    val decoratedType = it.typeMirror.asTypeName()
                    val decoration = ClassName(it.`package`, it.name)

                    FunSpec.builder("decorate")
                            .returns(decoratedType)
                            .addParameter("implementation", decoratedType)
                            .addParameter("coroutineContext", CoroutineContext::class)
                            .addStatement("return %T(implementation, coroutineContext)", decoration)
                            .build()
                }

        val source = FileSpec.get(aggregator.`package`, TypeSpec.objectBuilder(aggregator.name)
                .apply {
                    if (immutableHelperFunctions.isNotEmpty())
                        addFunctions(immutableHelperFunctions)
                }
                .build()
        )
        return source.toString()
    }
}