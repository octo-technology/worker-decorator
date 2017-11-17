package com.octo.workerdecorator.processor

import com.nhaarman.mockito_kotlin.mock
import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Language.JAVA
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import com.octo.workerdecorator.processor.sourcewriter.JavaSourceWriter
import com.octo.workerdecorator.processor.sourcewriter.KotlinSourceWriter
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SourceWriterFactoryTest {

    @Test
    fun `Factory returns the Kotlin writer`() {
        // Given
        val configuration = Configuration(KOTLIN, mock(), mock())
        val factory = SourceWriterFactory(mock(), mock())

        // When
        val writer = factory.make(configuration)

        // Then
        assertThat(writer).isExactlyInstanceOf(KotlinSourceWriter::class.java)
    }

    @Test
    fun `Factory returns the Java writer`() {
        // Given
        val configuration = Configuration(JAVA, mock(), mock())
        val factory = SourceWriterFactory(mock(), mock())

        // When
        val writer = factory.make(configuration)

        // Then
        assertThat(writer).isExactlyInstanceOf(JavaSourceWriter::class.java)
    }
}