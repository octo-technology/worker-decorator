package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Document

interface Generator {
    fun generate(document: Document): String
}