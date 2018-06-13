package com.octo.workerdecorator.processor

import com.nhaarman.mockito_kotlin.mock
import com.octo.workerdecorator.processor.entity.AggregateConfiguration
import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Implementation.EXECUTOR
import com.octo.workerdecorator.processor.entity.Language.JAVA
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import com.octo.workerdecorator.processor.entity.Mutability.IMMUTABLE
import com.octo.workerdecorator.processor.entity.Mutability.MUTABLE
import com.octo.workerdecorator.processor.generator.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GeneratorFactoryTest {

    @Test
    fun `returns a Java immutable generator`() {
        // Given
        val reader = GeneratorFactory()
        val configuration = Configuration(JAVA, mock(), IMMUTABLE)

        // When
        val result = reader.make(configuration)

        // Then
        assertThat(result).isExactlyInstanceOf(JavaImmutableExecutorGenerator::class.java)
    }

    @Test
    fun `returns a Java mutable generator`() {
        // Given
        val reader = GeneratorFactory()
        val configuration = Configuration(JAVA, mock(), MUTABLE)

        // When
        val result = reader.make(configuration)

        // Then
        assertThat(result).isExactlyInstanceOf(JavaMutableExecutorGenerator::class.java)
    }

    @Test
    fun `returns a Kotlin immutable generator`() {
        // Given
        val reader = GeneratorFactory()
        val configuration = Configuration(KOTLIN, mock(), IMMUTABLE)

        // When
        val result = reader.make(configuration)

        // Then
        assertThat(result).isExactlyInstanceOf(KotlinImmutableExecutorGenerator::class.java)
    }

    @Test
    fun `returns a Kotlin mutable generator`() {
        // Given
        val reader = GeneratorFactory()
        val configuration = Configuration(KOTLIN, mock(), MUTABLE)

        // When
        val result = reader.make(configuration)

        // Then
        assertThat(result).isExactlyInstanceOf(KotlinMutableExecutorGenerator::class.java)
    }

    @Test
    fun `returns a Kotlin executor aggregate generator`() {
        // Given
        // Given
        val reader = GeneratorFactory()
        val configuration = AggregateConfiguration(KOTLIN, EXECUTOR)

        // When
        val result = reader.makeAggregator(configuration)

        // Then
        assertThat(result).isExactlyInstanceOf(KotlinExecutorAggregatorGenerator::class.java)
    }

    @Test
    fun `returns a no-op aggregate generator`() {
        // Given
        val reader = GeneratorFactory()
        val configuration = AggregateConfiguration(mock(), mock())

        // When
        val result = reader.makeAggregator(configuration)

        // Then
        assertThat(result).isExactlyInstanceOf(EmptyAggregateGenerator::class.java)
    }
}