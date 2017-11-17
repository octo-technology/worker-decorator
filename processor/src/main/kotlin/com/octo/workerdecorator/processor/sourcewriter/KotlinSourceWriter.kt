package com.octo.workerdecorator.processor.sourcewriter

import com.octo.workerdecorator.processor.SourceWriter
import com.octo.workerdecorator.processor.entity.Document
import java.io.File

class KotlinSourceWriter(private val kotlinFolder: File) : SourceWriter {

    override fun write(document: Document, source: String) {
        File(kotlinFolder, "${document.name}.kt")
                .bufferedWriter()
                .use { it.write(source) }
    }
}