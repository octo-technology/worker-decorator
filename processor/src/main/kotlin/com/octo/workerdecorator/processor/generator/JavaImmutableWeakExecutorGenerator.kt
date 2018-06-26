package com.octo.workerdecorator.processor.generator

import com.octo.workerdecorator.processor.Generator
import com.octo.workerdecorator.processor.entity.DecorationDocument
import com.octo.workerdecorator.processor.extension.asTypeName
import com.squareup.javapoet.*
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.lang.ref.WeakReference
import java.util.concurrent.Executor
import javax.lang.model.element.Modifier.*

class JavaImmutableWeakExecutorGenerator : Generator {

    override fun generate(document: DecorationDocument): String {

        val decoratedType = document.typeMirror.asTypeName()

        val methods = document.methods.map {

            val parameters = it.parameters.map {
                ParameterSpec.builder(it.typeMirror.asTypeName(), it.name)
                    .addAnnotation(if (it.isOptional) Nullable::class.java else NotNull::class.java)
                    .addModifiers(FINAL)
                    .build()
            }
            val bodyParameters = it.parameters.joinToString(", ") { it.name }

            val runnable = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(Runnable::class.java)
                .addMethod(
                    MethodSpec.methodBuilder("run")
                        .addAnnotation(Override::class.java)
                        .addModifiers(PUBLIC)
                        .addCode(
                            CodeBlock.builder()
                                .addStatement("\$T ref = decorated.get()", decoratedType)
                                .beginControlFlow("if (ref != null)")
                                .addStatement("ref.${it.name}($bodyParameters)")
                                .endControlFlow()
                                .build()
                        )
                        .build()
                )
                .build()

            MethodSpec.methodBuilder(it.name)
                .addAnnotation(Override::class.java)
                .addModifiers(PUBLIC)
                .addParameters(parameters)
                .addStatement("executor.execute(\$L)", runnable)
                .build()
        }

        val weakReferenceType = ClassName.get(WeakReference::class.java)
        val weakDecoratedType = ParameterizedTypeName.get(weakReferenceType, decoratedType)

        return JavaFile.builder(
            document.`package`,
            TypeSpec.classBuilder(document.name)
                .addModifiers(PUBLIC, FINAL)
                .addSuperinterface(decoratedType)
                .addMethod(
                    MethodSpec.constructorBuilder()
                        .addModifiers(PUBLIC)
                        .addParameter(
                            ParameterSpec.builder(decoratedType, "decorated")
                                .addAnnotation(NotNull::class.java)
                                .build()
                        )
                        .addParameter(
                            ParameterSpec.builder(Executor::class.java, "executor")
                                .addAnnotation(NotNull::class.java)
                                .build()
                        )
                        .addStatement("this.decorated = new WeakReference<>(decorated)")
                        .addStatement("this.executor = executor")
                        .build()
                )
                .addField(weakDecoratedType, "decorated", PRIVATE, FINAL)
                .addField(Executor::class.java, "executor", PRIVATE, FINAL)
                .addMethods(methods)
                .build()
        )
            .build()
            .toString()
    }
}