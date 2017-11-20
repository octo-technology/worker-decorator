package com.octo.workerdecorator.processor.generator

import com.octo.workerdecorator.processor.Generator
import com.octo.workerdecorator.processor.entity.Document
import com.octo.workerdecorator.processor.extension.asTypeName
import com.squareup.javapoet.*
import java.util.concurrent.Executor
import javax.annotation.Nonnull
import javax.annotation.Nullable
import javax.lang.model.element.Modifier.*

class JavaMutableExecutorGenerator : Generator {

    override fun generate(document: Document): String {
        val methods = document.methods.map {

            val parameters = it.parameters.map { ParameterSpec.builder(it.typeMirror.asTypeName(), it.name).build() }
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
                        .addSuperinterface(document.typeMirror.asTypeName())
                        .addMethod(MethodSpec.constructorBuilder()
                                .addModifiers(PUBLIC)
                                .addParameter(ParameterSpec.builder(Executor::class.java, "executor")
                                        .addAnnotation(Nonnull::class.java)
                                        .build())
                                .addStatement("this.executor = executor")
                                .build())
                        .addField(Executor::class.java, "executor", PRIVATE, FINAL)
                        .addField(FieldSpec.builder(document.typeMirror.asTypeName(), "decorated")
                                .addAnnotation(Nullable::class.java)
                                .addModifiers(PRIVATE)
                                .build())
                        .addMethod(MethodSpec.methodBuilder("setDecorated")
                                .addModifiers(PUBLIC)
                                .addParameter(ParameterSpec.builder(document.typeMirror.asTypeName(), "decorated")
                                        .addAnnotation(Nullable::class.java)
                                        .build())
                                .addStatement("this.decorated = decorated")
                                .build())
                        .addMethods(methods)
                        .build())
                .build()
                .toString()
    }
}