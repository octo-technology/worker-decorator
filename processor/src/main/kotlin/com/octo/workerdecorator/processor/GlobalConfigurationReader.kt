package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Implementation
import com.octo.workerdecorator.processor.entity.Language

/**
 * Contract for classes capable of reading the module global options (language and implementation method)
 */
interface GlobalConfigurationReader {

    val language: Language
    val implementation: Implementation
}