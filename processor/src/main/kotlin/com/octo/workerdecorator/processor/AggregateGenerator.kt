package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.AggregateDocument
import com.octo.workerdecorator.processor.entity.Document

interface AggregateGenerator {
    fun generate(aggregator: Document, documents: List<AggregateDocument>): String
}