package com.octo.workerdecorator.processor

import com.octo.workerdecorator.annotation.Decorate
import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Implementation.EXECUTOR
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import com.octo.workerdecorator.processor.entity.Mutability.MUTABLE
import com.octo.workerdecorator.processor.entity.Mutability.IMMUTABLE

/**
 * Class responsible for creating a [Configuration] object describing the wanted decoration
 *
 * It is currently almost a "mock".
 * The way of configuring the processor (through the annotation, gradle settings…)
 * has to be discussed.
 */
class ConfigurationReader {

    // This is partially "mocked" for now
    fun read(annotation: Decorate): Configuration =
            when (annotation.decoratedIsMutable) {
                true -> Configuration(KOTLIN, EXECUTOR, MUTABLE)
                false -> Configuration(KOTLIN, EXECUTOR, IMMUTABLE)
            }
}