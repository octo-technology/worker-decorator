package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Mutability.MUTABLE
import com.octo.workerdecorator.processor.entity.Mutability.UNMUTABLE
import com.octo.workerdecorator.processor.generator.KotlinMutableExecutorGenerator
import com.octo.workerdecorator.processor.generator.KotlinUnmutableExecutorGenerator

/**
 * Class responsible for creating the [Generator] corresponding to the given [Configuration]
 */
class GeneratorFactory {

    // This is partially "mocked" for now
    fun make(configuration: Configuration): Generator =
            when (configuration.mutability) {
                UNMUTABLE -> KotlinUnmutableExecutorGenerator()
                MUTABLE -> KotlinMutableExecutorGenerator()
            }
}