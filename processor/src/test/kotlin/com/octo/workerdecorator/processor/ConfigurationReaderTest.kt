package com.octo.workerdecorator.processor

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.octo.workerdecorator.annotation.Decorate
import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Implementation.EXECUTOR
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import com.octo.workerdecorator.processor.entity.Mutability.MUTABLE
import com.octo.workerdecorator.processor.entity.Mutability.IMMUTABLE
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ConfigurationReaderTest {

    @Test
    fun `return a mutable configuration`() {
        // Given
        val reader = ConfigurationReader()
        val expected = Configuration(KOTLIN, EXECUTOR, MUTABLE)

        val annotation: Decorate = mock()
        given(annotation.decoratedIsMutable).willReturn(true)

        // When
        val result = reader.read(annotation)

        // Then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `return an immutable configuration`() {
        // Given
        val reader = ConfigurationReader()
        val expected = Configuration(KOTLIN, EXECUTOR, IMMUTABLE)

        val annotation: Decorate = mock()
        given(annotation.decoratedIsMutable).willReturn(false)

        // When
        val result = reader.read(annotation)

        // Then
        assertThat(result).isEqualTo(expected)
    }
}