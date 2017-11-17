package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Document
import java.io.File

interface SourceWriter {

    fun write(document: Document, source: String)
}