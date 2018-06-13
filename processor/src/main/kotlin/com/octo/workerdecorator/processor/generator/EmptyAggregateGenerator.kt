package com.octo.workerdecorator.processor.generator

import com.octo.workerdecorator.processor.AggregateGenerator
import com.octo.workerdecorator.processor.entity.AggregateDocument
import com.octo.workerdecorator.processor.entity.Document

class EmptyAggregateGenerator : AggregateGenerator {

    override fun generate(aggregator: Document, documents: List<AggregateDocument>): String {
        return String()
    }
}