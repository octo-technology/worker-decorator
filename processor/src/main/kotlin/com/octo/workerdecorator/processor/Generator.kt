package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.DecorationDocument

/**
 * Contract for classes capable of converting a [DecorationDocument] entity into a source code as a [String]
 */
interface Generator {
    fun generate(document: DecorationDocument): String
}