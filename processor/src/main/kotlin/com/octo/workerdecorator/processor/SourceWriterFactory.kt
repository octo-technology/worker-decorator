package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Language.JAVA
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import com.octo.workerdecorator.processor.sourcewriter.JavaSourceWriter
import com.octo.workerdecorator.processor.sourcewriter.KotlinSourceWriter
import java.io.File
import javax.annotation.processing.Filer

/**
 * Class responsible for creating the [SourceWriter] corresponding to the language specified in the [Configuration]
 */
class SourceWriterFactory(private val kotlinFolder: File?, private val javaFiler: Filer) {

    fun make(configuration: Configuration): SourceWriter =
            // TODO Crash the processor if the wanted language is Kotlin and kotlinFolder is null
            when (configuration.language) {
                JAVA -> JavaSourceWriter(javaFiler)
                KOTLIN -> {
                    if (kotlinFolder == null) {
                        throw Error("workerdecorator-processor is set to generate kotlin sources, but the kotlin folder for the generated sources is null")
                    }
                    KotlinSourceWriter(kotlinFolder)
                }
            }
}