package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.AggregateConfiguration
import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Implementation.EXECUTOR
import com.octo.workerdecorator.processor.entity.Language.JAVA
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import com.octo.workerdecorator.processor.entity.Mutability.IMMUTABLE
import com.octo.workerdecorator.processor.entity.Mutability.MUTABLE
import com.octo.workerdecorator.processor.generator.*

/**
 * Class responsible for creating the [Generator] corresponding to the given [Configuration]
 */
class GeneratorFactory {

    // This is partially "mocked" for now
    fun make(configuration: Configuration): Generator =
        when (configuration.language) {
            JAVA -> getJavaDecorator(configuration)
            KOTLIN -> getKotlinDecorator(configuration)
        }

    private fun getJavaDecorator(configuration: Configuration): Generator =
        when (configuration.mutability) {
            IMMUTABLE -> JavaImmutableExecutorGenerator()
            MUTABLE -> JavaMutableExecutorGenerator()
        }

    private fun getKotlinDecorator(configuration: Configuration): Generator =
        when (configuration.mutability) {
            IMMUTABLE -> KotlinImmutableExecutorGenerator()
            MUTABLE -> KotlinMutableExecutorGenerator()
        }

    fun makeAggregator(configuration: AggregateConfiguration): AggregateGenerator =
        when (configuration) {
            AggregateConfiguration(KOTLIN, EXECUTOR) -> KotlinExecutorAggregatorGenerator()
            else -> EmptyAggregateGenerator()
        }
}