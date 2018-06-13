package com.octo.workerdecorator.processor.sourcewriter

import com.octo.workerdecorator.processor.SourceWriter
import com.octo.workerdecorator.processor.entity.Document
import javax.annotation.processing.Filer

class JavaSourceWriter(private val filer: Filer) : SourceWriter {

    override fun write(document: Document, source: String) {
        filer.createSourceFile("${document.`package`}.${document.name}")
            .openWriter()
            .buffered()
            .use { it.write(source) }
    }
}