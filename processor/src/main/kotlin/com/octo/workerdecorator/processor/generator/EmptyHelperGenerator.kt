package com.octo.workerdecorator.processor.generator

import com.octo.workerdecorator.processor.HelperGenerator
import com.octo.workerdecorator.processor.entity.HelperDocument
import com.octo.workerdecorator.processor.entity.Document

class EmptyHelperGenerator : HelperGenerator {

    override fun generate(aggregator: Document, documents: List<HelperDocument>): String {
        return String()
    }
}