package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Implementation.EXECUTOR
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import com.octo.workerdecorator.processor.entity.Mutability.UNMUTABLE
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ConfigurationReaderTest {

    @Test
    fun `mock configuration fetching for now`() {
        // Given
        val reader = ConfigurationReader()
        val expected = Configuration(KOTLIN, EXECUTOR, UNMUTABLE)

        // When
        val result = reader.read()

        // Then
        assertThat(result).isEqualTo(expected)
    }
}