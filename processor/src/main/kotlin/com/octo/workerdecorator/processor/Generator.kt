package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.DecorationDocument

/**
 * Contract for classes capable of generating decoration source code
 */
interface Generator {
    fun generate(document: DecorationDocument): String
}