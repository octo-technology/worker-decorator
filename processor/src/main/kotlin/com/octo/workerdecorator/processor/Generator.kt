package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Document

/**
 * Contract for classes capable of converting a [Document] entity into a source code as a [String]
 */
interface Generator {
    fun generate(document: Document): String
}