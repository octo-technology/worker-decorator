package com.octo.workerdecorator.processor.generator

import com.octo.workerdecorator.annotation.java.WorkerDecoration
import com.octo.workerdecorator.processor.Generator
import com.octo.workerdecorator.processor.entity.DecorationDocument
import com.octo.workerdecorator.processor.extension.asTypeName
import com.squareup.javapoet.*
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.util.concurrent.Executor
import javax.lang.model.element.Modifier.*

class JavaMutableExecutorGenerator : Generator {

    override fun generate(document: DecorationDocument): String {
        val decoratedType = document.typeMirror.asTypeName()

        val methods = document.methods.map {

            val parameters = it.parameters.map {
                ParameterSpec.builder(it.typeMirror.asTypeName(), it.name)
                        .addAnnotation(if (it.isOptional) Nullable::class.java else NotNull::class.java)
                        .build()
            }
            val bodyParameters = it.parameters.joinToString(", ") { it.name }

            val comparator = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(Runnable::class.java)
                    .addMethod(MethodSpec.methodBuilder("run")
                            .addAnnotation(Override::class.java)
                            .addModifiers(PUBLIC)
                            .beginControlFlow("if (decorated != null)")
                            .addStatement("decorated.${it.name}($bodyParameters)")
                            .endControlFlow()
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
                        .addSuperinterface(decoratedType)
                        .addSuperinterface(
                            ParameterizedTypeName.get(ClassName.get(WorkerDecoration::class.java), decoratedType))
                        .addMethod(MethodSpec.constructorBuilder()
                                .addModifiers(PUBLIC)
                                .addParameter(ParameterSpec.builder(Executor::class.java, "executor")
                                        .addAnnotation(NotNull::class.java)
                                        .build())
                                .addStatement("this.executor = executor")
                                .build())
                        .addField(Executor::class.java, "executor", PRIVATE, FINAL)
                        .addField(FieldSpec.builder(decoratedType, "decorated")
                                .addAnnotation(Nullable::class.java)
                                .addModifiers(PRIVATE)
                                .build())
                        .addMethod(MethodSpec.methodBuilder("setDecorated")
                                .addAnnotation(Override::class.java)
                                .addModifiers(PUBLIC)
                                .addParameter(ParameterSpec.builder(decoratedType, "decorated")
                                        .addAnnotation(Nullable::class.java)
                                        .build())
                                .addStatement("this.decorated = decorated")
                                .build())
                        .addMethods(methods)
                        .addMethod(MethodSpec.methodBuilder("asType")
                            .addAnnotation(Override::class.java)
                            .addAnnotation(NotNull::class.java)
                            .addModifiers(PUBLIC)
                            .returns(decoratedType)
                            .addStatement("return this")
                            .build())
                        .build())
                .build()
                .toString()
    }
}