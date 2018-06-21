package com.octo.workerdecorator.processor

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.octo.workerdecorator.processor.entity.AggregateConfiguration
import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Implementation
import com.octo.workerdecorator.processor.entity.Implementation.EXECUTOR
import com.octo.workerdecorator.processor.entity.Language
import com.octo.workerdecorator.processor.entity.Language.JAVA
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import com.octo.workerdecorator.processor.entity.Mutability.IMMUTABLE
import com.octo.workerdecorator.processor.entity.Mutability.MUTABLE
import com.octo.workerdecorator.processor.entity.ReferenceStrength.STRONG
import com.octo.workerdecorator.processor.entity.ReferenceStrength.WEAK
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ConfigurationMakerTest {

    @Test
    fun `returns a Kotlin Executable Mutable Strong configuration`() {
        // Given
        val reader = ConfigurationMaker(object : ConfigurationReader {
            override val language = KOTLIN
            override val implementation = EXECUTOR
        })
        val expected = Configuration(KOTLIN, EXECUTOR, MUTABLE, STRONG)

        val annotation: com.octo.workerdecorator.annotation.Decorate = mock()
        given(annotation.mutable).willReturn(true)
        given(annotation.weak).willReturn(false)

        // When
        val result = reader.read(annotation)

        // Then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `returns a Java Executable Immutable Weak configuration`() {
        // Given
        val reader = ConfigurationMaker(object : ConfigurationReader {
            override val language = JAVA
            override val implementation = EXECUTOR
        })
        val expected = Configuration(JAVA, EXECUTOR, IMMUTABLE, WEAK)

        val annotation: com.octo.workerdecorator.annotation.Decorate = mock()
        given(annotation.mutable).willReturn(false)
        given(annotation.weak).willReturn(true)

        // When
        val result = reader.read(annotation)

        // Then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `returns an aggregate configuration`() {
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