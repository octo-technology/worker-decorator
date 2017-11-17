package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Implementation.EXECUTOR
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import com.octo.workerdecorator.processor.entity.Mutability.UNMUTABLE

/**
 * Class responsible for creating a [Configuration] object describing the wanted decorator
 *
 * It is currently almost a "mock".
 * The way of configuration the processor (through the annotation, gradle settings…)
 * has to be discussed.
 */
class ConfigurationReader {

    fun read(): Configuration {
        // This is "mocked" for now
        return Configuration(KOTLIN, EXECUTOR, UNMUTABLE)
    }
}