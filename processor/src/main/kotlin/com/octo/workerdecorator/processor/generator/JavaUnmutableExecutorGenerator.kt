package com.octo.workerdecorator.processor.generator

import com.octo.workerdecorator.processor.Generator
import com.octo.workerdecorator.processor.entity.Document
import com.octo.workerdecorator.processor.extension.asTypeName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeSpec
import java.util.concurrent.Executor
import javax.lang.model.element.Modifier.*

class JavaUnmutableExecutorGenerator : Generator {

    override fun generate(document: Document): String {

        val methods = document.methods.map {

            val parameters = it.parameters.map { ParameterSpec.builder(it.typeMirror.asTypeName(), it.name).build() }
            val bodyParameters = it.parameters.joinToString(", ") { it.name }

            val comparator = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(Runnable::class.java)
                    .addMethod(MethodSpec.methodBuilder("run")
                            .addAnnotation(Override::class.java)
                            .addModifiers(PUBLIC)
                            .addStatement("decorated.${it.name}($bodyParameters)")
                            .build())
                    .build()

            MethodSpec.methodBuilder(it.name)
                    .addAnnotation(Override::class.java)
                    .addModifiers(PUBLIC)
                    .addParameters(parameters)
                    .addStatement("executor.execute(\$L)", comparator)
                    .build()
        }

        return JavaFile.builder(document.`package`,
                TypeSpec.classBuilder(document.name)
                        .addModifiers(PUBLIC, FINAL)
                        .addSuperinterface(document.typeMirror.asTypeName())
                        .addMethod(MethodSpec.constructorBuilder()
                                .addModifiers(PUBLIC)
                                .addParameter(Executor::class.java, "executor")
                                .addParameter(document.typeMirror.asTypeName(), "decorated")
                                .addStatement("this.executor = executor")
                                .addStatement("this.decorated = decorated")
                                .build())
                        .addField(Executor::class.java, "executor", PRIVATE, FINAL)
                        .addField(document.typeMirror.asTypeName(), "decorated", PRIVATE, FINAL)
                        .addMethods(methods)
                        .build())
                .build()
                .toString()
    }
}