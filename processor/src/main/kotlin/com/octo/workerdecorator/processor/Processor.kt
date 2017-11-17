package com.octo.workerdecorator.processor

import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion.RELEASE_7
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

@Suppress("unused")
@SupportedSourceVersion(RELEASE_7)
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

        interactor = Interactor(
                Analyser(processingEnv.elementUtils),
                ConfigurationReader(),
                GeneratorFactory(),
                SourceWriterFactory(File(processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION]!!),
                        processingEnv.filer))
    }

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        annotations.map { roundEnv.getElementsAnnotatedWith(it) }
                .flatten()
                .filter { it.kind == ElementKind.INTERFACE }
                .map { it as TypeElement }
                .map { interactor.process(it) }

        return true
    }

}