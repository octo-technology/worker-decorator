package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.generator.KotlinImmutableExecutorGenerator

/**
 * Class responsible for creating the [Generator] corresponding to the given [Configuration]
 *
 * It is currently almost a "mock".
 */
class GeneratorFactory {

    fun make(configuration: Configuration): Generator {
        // This is "mocked" for now
        return KotlinImmutableExecutorGenerator()
    }
}