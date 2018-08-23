package com.octo.workerdecorator.processor.generator.kotlin

import com.octo.workerdecorator.processor.entity.HelperDocument
import com.octo.workerdecorator.processor.test.CompilationAwareTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KotlinCoroutinesHelperGeneratorTest : CompilationAwareTest() {
    @Test
    fun `it generates the wanted kotlin file`() {
        // Given
        val generator = KotlinCoroutinesHelperGenerator()

        // When
        val result = generator.generate(HelperDocument.AGGREGATOR, aggregateFixture())

        // Then
        val targetContent = readResource("ExpectedWorkerDecoratorCoroutines.kt")
        assertThat(result).isEqualTo(targetContent)
    }
}