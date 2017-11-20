package com.octo.workerdecorator.processor

import com.nhaarman.mockito_kotlin.mock
import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Language.JAVA
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import com.octo.workerdecorator.processor.entity.Mutability.MUTABLE
import com.octo.workerdecorator.processor.entity.Mutability.UNMUTABLE
import com.octo.workerdecorator.processor.generator.JavaMutableExecutorGenerator
import com.octo.workerdecorator.processor.generator.JavaUnmutableExecutorGenerator
import com.octo.workerdecorator.processor.generator.KotlinMutableExecutorGenerator
import com.octo.workerdecorator.processor.generator.KotlinUnmutableExecutorGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GeneratorFactoryTest {

    @Test
    fun `returns a Java unmutable generator`() {
        // Given
        val reader = GeneratorFactory()
        val configuration = Configuration(JAVA, mock(), UNMUTABLE)

        // When
        val result = reader.make(configuration)

        // Then
        assertThat(result).isExactlyInstanceOf(JavaUnmutableExecutorGenerator::class.java)
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
    fun `returns a Kotlin an unmutable generator`() {
        // Given
        val reader = GeneratorFactory()
        val configuration = Configuration(KOTLIN, mock(), UNMUTABLE)

        // When
        val result = reader.make(configuration)

        // Then
        assertThat(result).isExactlyInstanceOf(KotlinUnmutableExecutorGenerator::class.java)
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
}