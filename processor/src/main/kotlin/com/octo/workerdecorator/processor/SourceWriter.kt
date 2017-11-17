package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Document

interface SourceWriter {
    fun write(document: Document, source: String)
}