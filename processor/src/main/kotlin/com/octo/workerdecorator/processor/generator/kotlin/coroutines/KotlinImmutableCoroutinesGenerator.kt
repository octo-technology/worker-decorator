package com.octo.workerdecorator.processor.generator.kotlin.coroutines

import com.octo.kotlinelements.asKotlinTypeName
import com.octo.workerdecorator.processor.Generator
import com.octo.workerdecorator.processor.entity.DecorationDocument
import com.squareup.kotlinpoet.*
import kotlin.coroutines.experimental.CoroutineContext

class KotlinImmutableCoroutinesGenerator : Generator {

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
                    .addModifiers(KModifier.OVERRIDE)
                    .addParameters(specParameters)
                    .addStatement("launch(coroutineContext) { decorated.${it.name}($bodyParameters) }")
                    .build()
        }.asIterable()
        val decoration = TypeSpec.classBuilder(document.name)
                .addSuperinterface(decoratedType)
                .primaryConstructor(
                        FunSpec.constructorBuilder()
                                .addParameter("decorated", decoratedType)
                                .addParameter("coroutineContext", CoroutineContext::class)
                                .build()
                )
                .addProperty(PropertySpec.builder("decorated", decoratedType, KModifier.PRIVATE).initializer("decorated").build())
                .addProperty(PropertySpec.builder("coroutineContext", CoroutineContext::class, KModifier.PRIVATE).initializer("coroutineContext").build())
                .addFunctions(functions)
                .build()

        val source = FileSpec.builder(document.`package`, document.name)
                .addStaticImport("kotlinx.coroutines.experimental", "launch")
                .addType(decoration)
                .build()
        return source.toString()
    }

}