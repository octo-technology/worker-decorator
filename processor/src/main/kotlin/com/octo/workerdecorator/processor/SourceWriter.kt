package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Document

/**
 * Contract for classes capable of writing an input [String] source code into a file
 */
interface SourceWriter {
    fun write(document: Document, source: String)
}