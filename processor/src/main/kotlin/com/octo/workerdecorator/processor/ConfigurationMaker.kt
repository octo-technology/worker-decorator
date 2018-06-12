package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Mutability.IMMUTABLE
import com.octo.workerdecorator.processor.entity.Mutability.MUTABLE

/**
 * Class responsible for creating a [Configuration] object describing the wanted decoration
 *
 * It is currently almost a "mock".
 * The way of configuring the processor (through the com.octo.workerdecorator.annotation, gradle settings…)
 * has to be discussed.
 */
class ConfigurationMaker(private val configurationReader: ConfigurationReader) {

    // This is partially "mocked" for now
    fun read(annotation: com.octo.workerdecorator.annotation.Decorate): Configuration {
        val language = configurationReader.language
        val implementation = configurationReader.implementation

        return when (annotation.mutable) {
            true -> Configuration(language, implementation, MUTABLE)
            false -> Configuration(language, implementation, IMMUTABLE)
        }
    }
}