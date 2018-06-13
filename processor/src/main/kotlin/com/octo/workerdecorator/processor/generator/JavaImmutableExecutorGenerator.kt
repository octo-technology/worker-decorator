package com.octo.workerdecorator.processor.generator

import com.octo.workerdecorator.processor.Generator
import com.octo.workerdecorator.processor.entity.DecorationDocument
import com.octo.workerdecorator.processor.extension.asTypeName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeSpec
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.util.concurrent.Executor
import javax.lang.model.element.Modifier.*

class JavaImmutableExecutorGenerator : Generator {

    override fun generate(document: DecorationDocument): String {

        val methods = document.methods.map {

            val parameters = it.parameters.map {
                ParameterSpec.builder(it.typeMirror.asTypeName(), it.name)
                    .addAnnotation(if (it.isOptional) Nullable::class.java else NotNull::class.java)
                    .build()
            }
            val bodyParameters = it.parameters.joinToString(", ") { it.name }

            val comparator = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(Runnable::class.java)
                .addMethod(
                    MethodSpec.methodBuilder("run")
                        .addAnnotation(Override::class.java)
                        .addModifiers(PUBLIC)
                        .addStatement("decorated.${it.name}($bodyParameters)")
                        .build()
                )
                .build()

            MethodSpec.methodBuilder(it.name)
                .addAnnotation(Override::class.java)
                .addModifiers(PUBLIC)
                .addParameters(parameters)
                .addStatement("executor.execute(\$L)", comparator)
                .build()
        }

        return JavaFile.builder(
            document.`package`,
            TypeSpec.classBuilder(document.name)
                .addModifiers(PUBLIC, FINAL)
                .addSuperinterface(document.typeMirror.asTypeName())
                .addMethod(
                    MethodSpec.constructorBuilder()
                        .addModifiers(PUBLIC)
                        .addParameter(
                            ParameterSpec.builder(document.typeMirror.asTypeName(), "decorated")
                                .addAnnotation(NotNull::class.java)
                                .build()
                        )
                        .addParameter(
                            ParameterSpec.builder(Executor::class.java, "executor")
                                .addAnnotation(NotNull::class.java)
                                .build()
                        )
                        .addStatement("this.decorated = decorated")
                        .addStatement("this.executor = executor")
                        .build()
                )
                .addField(document.typeMirror.asTypeName(), "decorated", PRIVATE, FINAL)
                .addField(Executor::class.java, "executor", PRIVATE, FINAL)
                .addMethods(methods)
                .build()
        )
            .build()
            .toString()
    }
}