package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.HelperConfiguration
import com.octo.workerdecorator.processor.entity.Implementation.EXECUTOR
import com.octo.workerdecorator.processor.entity.Language.JAVA
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import com.octo.workerdecorator.processor.entity.Mutability.IMMUTABLE
import com.octo.workerdecorator.processor.entity.Mutability.MUTABLE
import com.octo.workerdecorator.processor.entity.ReferenceStrength.STRONG
import com.octo.workerdecorator.processor.entity.ReferenceStrength.WEAK
import com.octo.workerdecorator.processor.generator.*

/**
 * Class responsible for creating the generators corresponding to the given configuration
 */
class GeneratorFactory {

    fun make(configuration: Configuration): Generator =
        when (configuration) {
            Configuration(KOTLIN, EXECUTOR, IMMUTABLE, STRONG) -> KotlinImmutableExecutorGenerator()
            Configuration(KOTLIN, EXECUTOR, IMMUTABLE, WEAK) -> KotlinImmutableWeakExecutorGenerator()
            Configuration(KOTLIN, EXECUTOR, MUTABLE, STRONG) -> KotlinMutableExecutorGenerator()
            Configuration(KOTLIN, EXECUTOR, MUTABLE, WEAK) -> KotlinMutableWeakExecutorGenerator()
            Configuration(JAVA, EXECUTOR, IMMUTABLE, STRONG) -> JavaImmutableExecutorGenerator()
            Configuration(JAVA, EXECUTOR, IMMUTABLE, WEAK) -> JavaImmutableWeakExecutorGenerator()
            Configuration(JAVA, EXECUTOR, MUTABLE, STRONG) -> JavaMutableExecutorGenerator()
            Configuration(JAVA, EXECUTOR, MUTABLE, WEAK) -> JavaMutableWeakExecutorGenerator()
            else -> throw Exception("No generator for $configuration")
        }

    fun makeAggregator(configuration: HelperConfiguration): HelperGenerator =
        when (configuration) {
            HelperConfiguration(KOTLIN, EXECUTOR) -> KotlinExecutorHelperGenerator()
            HelperConfiguration(JAVA, EXECUTOR) -> JavaExecutorHelperGenerator()
            else -> EmptyHelperGenerator()
        }
}