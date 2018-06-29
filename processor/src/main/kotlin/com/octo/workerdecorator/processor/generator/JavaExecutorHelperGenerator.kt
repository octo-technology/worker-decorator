package com.octo.workerdecorator.processor.generator

import com.octo.workerdecorator.WorkerDecoration
import com.octo.workerdecorator.processor.HelperGenerator
import com.octo.workerdecorator.processor.entity.HelperDocument
import com.octo.workerdecorator.processor.entity.DecorationDocument
import com.octo.workerdecorator.processor.entity.Document
import com.octo.workerdecorator.processor.entity.Mutability.IMMUTABLE
import com.octo.workerdecorator.processor.entity.Mutability.MUTABLE
import com.octo.workerdecorator.processor.extension.asTypeName
import com.squareup.javapoet.*
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.lang.*
import java.lang.ref.WeakReference
import java.util.concurrent.Executor
import javax.lang.model.element.Modifier.*

class JavaExecutorHelperGenerator : HelperGenerator {

    override fun generate(aggregator: Document, documents: List<HelperDocument>): String {

        val executorType = ClassName.get(Executor::class.java)

        val immutableHelperFunctions = documents
            .filter { it.mutability == IMMUTABLE }
            .map {
                val decoratedType = it.typeMirror.asTypeName()
                val decoration = ClassName.get(it.`package`, it.name)

                MethodSpec.methodBuilder("decorate")
                    .returns(decoratedType)
                    .addModifiers(PUBLIC, STATIC)
                    .addParameter(decoratedType, "implementation")
                    .addParameter(executorType, "executor")
                    .addStatement("return new \$T(implementation, executor)", decoration)
                    .build()
            }

        val workerDecorationType = ClassName.get(WorkerDecoration::class.java)
        val classType = ClassName.get(Class::class.java)
        val exceptionType = ClassName.get(RuntimeException::class.java)
        val decorated = TypeVariableName.get("DECORATED")
        val parameterizedTypeName = ParameterizedTypeName.get(workerDecorationType, decorated)
        val clazzTypeName = ParameterizedTypeName.get(classType, decorated)

        val mutableHelperFunction = MethodSpec.methodBuilder("decorate")
            .addAnnotation(
                AnnotationSpec.builder(SuppressWarnings::class.java)
                    .addMember("value", "\$S", "unchecked")
                    .build()
            )
            .addModifiers(PUBLIC, STATIC)
            .addTypeVariable(decorated)
            .addParameter(clazzTypeName, "clazz")
            .addParameter(executorType, "executor")
            .returns(parameterizedTypeName)
            .addCode(CodeBlock.builder()
                .apply {
                    documents
                        .filter { it.mutability == MUTABLE }
                        .map {
                            val decoration = ClassName.get(it.`package`, it.name)
                            beginControlFlow("if (clazz == \$T.class)", it.typeMirror.asTypeName())
                                .addStatement("return (\$T) new \$T(executor)", parameterizedTypeName, decoration)
                                .endControlFlow()
                        }
                }
                .addStatement(
                    "throw new \$T(\"Impossible to find a decoration for \" + clazz.getSimpleName())",
                    exceptionType
                )
                .build())
            .build()

        return JavaFile.builder(
            aggregator.`package`,
            TypeSpec.classBuilder(aggregator.name)
                .addModifiers(PUBLIC)
                .apply {
                    if (documents.any { it.mutability == MUTABLE })
                        addMethod(mutableHelperFunction)
                }
                .apply {
                    if (documents.any { it.mutability == IMMUTABLE })
                        addMethods(immutableHelperFunctions)
                }
                .build()
        )
            .build()
            .toString()
    }

    fun generate(document: DecorationDocument): String {
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
                .addMethod(
                    MethodSpec.methodBuilder("run")
                        .addAnnotation(Override::class.java)
                        .addModifiers(PUBLIC)
                        .beginControlFlow("if (decorated != null)")
                        .addStatement("\$T ref = decorated.get()", decoratedType)
                        .beginControlFlow("if (ref != null)")
                        .addStatement("ref.${it.name}($bodyParameters)")
                        .endControlFlow()
                        .endControlFlow()
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

        val weakReferenceType = ClassName.get(WeakReference::class.java)
        val weakDecoratedType = ParameterizedTypeName.get(weakReferenceType, decoratedType)

        return JavaFile.builder(
            document.`package`,
            TypeSpec.classBuilder(document.name)
                .addModifiers(PUBLIC, FINAL)
                .addSuperinterface(decoratedType)
                .addSuperinterface(
                    ParameterizedTypeName.get(ClassName.get(WorkerDecoration::class.java), decoratedType)
                )
                .addMethod(
                    MethodSpec.constructorBuilder()
                        .addModifiers(PUBLIC)
                        .addParameter(
                            ParameterSpec.builder(Executor::class.java, "executor")
                                .addAnnotation(NotNull::class.java)
                                .build()
                        )
                        .addStatement("this.executor = executor")
                        .build()
                )
                .addField(Executor::class.java, "executor", PRIVATE, FINAL)
                .addField(
                    FieldSpec.builder(weakDecoratedType, "decorated")
                        .addAnnotation(Nullable::class.java)
                        .addModifiers(PRIVATE)
                        .build()
                )
                .addMethod(
                    MethodSpec.methodBuilder("setDecorated")
                        .addAnnotation(Override::class.java)
                        .addModifiers(PUBLIC)
                        .addParameter(
                            ParameterSpec.builder(decoratedType, "decorated")
                                .addAnnotation(Nullable::class.java)
                                .build()
                        )
                        .addStatement("this.decorated = new WeakReference<>(decorated)")
                        .build()
                )
                .addMethods(methods)
                .addMethod(
                    MethodSpec.methodBuilder("asType")
                        .addAnnotation(Override::class.java)
                        .addAnnotation(NotNull::class.java)
                        .addModifiers(PUBLIC)
                        .returns(decoratedType)
                        .addStatement("return this")
                        .build()
                )
                .build()
        )
            .build()
            .toString()
    }
}