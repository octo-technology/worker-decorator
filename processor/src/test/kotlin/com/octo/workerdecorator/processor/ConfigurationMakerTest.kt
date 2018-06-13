package com.octo.workerdecorator.processor

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.octo.workerdecorator.processor.entity.AggregateConfiguration
import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Implementation
import com.octo.workerdecorator.processor.entity.Implementation.COROUTINE
import com.octo.workerdecorator.processor.entity.Implementation.EXECUTOR
import com.octo.workerdecorator.processor.entity.Language
import com.octo.workerdecorator.processor.entity.Language.JAVA
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import com.octo.workerdecorator.processor.entity.Mutability.IMMUTABLE
import com.octo.workerdecorator.processor.entity.Mutability.MUTABLE
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ConfigurationMakerTest {

    @Test
    fun `return kotlin coroutine mutable configuration`() {
        // Given
        val reader = ConfigurationMaker(object : ConfigurationReader {
            override val language = KOTLIN
            override val implementation = COROUTINE
        })
        val expected = Configuration(KOTLIN, COROUTINE, MUTABLE)

        val annotation: com.octo.workerdecorator.annotation.Decorate = mock()
        given(annotation.mutable).willReturn(true)

        // When
        val result = reader.read(annotation)

        // Then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `return a java executor immutable configuration`() {
        // Given
        val reader = ConfigurationMaker(object : ConfigurationReader {
            override val language = JAVA
            override val implementation = EXECUTOR
        })
        val expected = Configuration(JAVA, EXECUTOR, IMMUTABLE)

        val annotation: com.octo.workerdecorator.annotation.Decorate = mock()
        given(annotation.mutable).willReturn(false)

        // When
        val result = reader.read(annotation)

        // Then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `return an aggregate configuration`() {
        // Given
        val conf: ConfigurationReader = object : ConfigurationReader {
            override val language = mock<Language>()
            override val implementation = mock<Implementation>()
        }
        val reader = ConfigurationMaker(conf)
        val expected = AggregateConfiguration(conf.language, conf.implementation)

        // When
        val result = reader.read()

        // Then
        assertThat(result).isEqualTo(expected)
    }
}