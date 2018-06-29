package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Document
import com.octo.workerdecorator.processor.entity.HelperDocument

/**
 * Contract for classes capable of generating helper methods
 */
interface HelperGenerator {
    fun generate(aggregator: Document, documents: List<HelperDocument>): String
}