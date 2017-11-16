package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.generator.KotlinImmutableExecutorGenerator

class GeneratorFactory {

    fun make(configuration: Configuration): Generator {
        // This is "mocked" for now
        return KotlinImmutableExecutorGenerator()
    }
}