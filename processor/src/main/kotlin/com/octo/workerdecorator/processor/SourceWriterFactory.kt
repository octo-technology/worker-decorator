package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Language
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

    fun make(language: Language): SourceWriter =
        when (language) {
            JAVA -> JavaSourceWriter(javaFiler)
            KOTLIN -> KotlinSourceWriter(kotlinFolder!!)
        }
}