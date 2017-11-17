package com.octo.workerdecorator.processor

import com.nhaarman.mockito_kotlin.mock
import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Mutability.MUTABLE
import com.octo.workerdecorator.processor.entity.Mutability.UNMUTABLE
import com.octo.workerdecorator.processor.generator.KotlinMutableExecutorGenerator
import com.octo.workerdecorator.processor.generator.KotlinUnmutableExecutorGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GeneratorFactoryTest {

    @Test
    fun `return an unmutable generator`() {
        // Given
        val reader = GeneratorFactory()
        val configuration = Configuration(mock(), mock(), UNMUTABLE)

        // When
        val result = reader.make(configuration)

        // Then
        assertThat(result).isExactlyInstanceOf(KotlinUnmutableExecutorGenerator::class.java)
    }

    @Test
    fun `return a mutable generator`() {
        // Given
        val reader = GeneratorFactory()
        val configuration = Configuration(mock(), mock(), MUTABLE)

        // When
        val result = reader.make(configuration)

        // Then
        assertThat(result).isExactlyInstanceOf(KotlinMutableExecutorGenerator::class.java)
    }
}