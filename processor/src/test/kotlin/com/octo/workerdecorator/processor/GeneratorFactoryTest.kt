package com.octo.workerdecorator.processor

import com.nhaarman.mockito_kotlin.mock
import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.generator.KotlinImmutableExecutorGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GeneratorFactoryTest {

    @Test
    fun `always return a KotlinImmutableExecutorGenerator for now`() {
        // Given
        val reader = GeneratorFactory()
        val configuration: Configuration = mock()

        // When
        val result = reader.make(configuration)

        // Then
        assertThat(result).isExactlyInstanceOf(KotlinImmutableExecutorGenerator::class.java)
    }
}