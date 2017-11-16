package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Document
import java.io.File

class SourceWriter(private val file: File) {

    fun write(document: Document, source: String) {
        File(file, "${document.name}.kt")
                .bufferedWriter()
                .use { it.write(source) }
    }
}