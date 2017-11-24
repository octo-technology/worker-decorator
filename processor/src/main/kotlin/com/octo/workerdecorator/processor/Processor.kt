package com.octo.workerdecorator.processor

import com.octo.workerdecorator.annotation.Decorate
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

/**
 * The processor called by annotation processing tools
 *
 * It contains no logic, all the processing is delegated to an [Interactor] instance.
 */
@Suppress("unused")
@SupportedAnnotationTypes("com.octo.workerdecorator.annotation.Decorate")
@SupportedOptions(Processor.GENERATE_KOTLIN_CODE_OPTION)
open class Processor : AbstractProcessor() {

    companion object {
        const val GENERATE_KOTLIN_CODE_OPTION = "generate.kotlin.code"
        const val KAPT_KOTLIN_GENERATED_OPTION = "kapt.kotlin.generated"
    }

    private lateinit var interactor: Interactor

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)

        val javaFiler = processingEnv.filer
        val kotlinPath = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION]
        val kotlinFolder = if (kotlinPath != null) File(kotlinPath) else null
        kotlinFolder?.mkdirs()

        interactor = Interactor(
                Analyser(processingEnv.elementUtils),
                ConfigurationReader(),
                GeneratorFactory(),
                SourceWriterFactory(kotlinFolder, javaFiler))
    }

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        annotations.map { roundEnv.getElementsAnnotatedWith(it) }
                .flatten()
                .filter { it.kind == ElementKind.INTERFACE }
                .map { it as TypeElement }
                .map { interactor.process(it, it.getAnnotation(Decorate::class.java)) }

        return true
    }

    override fun getSupportedSourceVersion(): SourceVersion
            = SourceVersion.latestSupported()
}