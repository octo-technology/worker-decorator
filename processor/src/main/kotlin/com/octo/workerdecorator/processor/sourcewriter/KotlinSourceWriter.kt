package com.octo.workerdecorator.processor.sourcewriter

import com.octo.workerdecorator.processor.SourceWriter
import com.octo.workerdecorator.processor.entity.Document
import com.octo.workerdecorator.processor.extension.children
import java.io.File

class KotlinSourceWriter(private val kotlinFolder: File) : SourceWriter {

    override fun write(document: Document, source: String) {
        kotlinFolder.children("${document.name}.kt", document.`package`)
                .bufferedWriter()
                .use { it.write(source) }
    }
}