package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Implementation
import com.octo.workerdecorator.processor.entity.Language

interface ConfigurationReader {

    val language: Language
    val implementation: Implementation
}