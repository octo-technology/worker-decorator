package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Document
import javax.annotation.processing.Filer

class SourceWriter(private val filer: Filer) {

    fun write(document: Document, source: String) {
        filer.createSourceFile(document.name)
                .openWriter()
                .buffered()
                .use { it.write(source) }
    }
}