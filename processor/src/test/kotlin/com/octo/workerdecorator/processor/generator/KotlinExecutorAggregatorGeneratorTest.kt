package com.octo.workerdecorator.processor.generator

import com.octo.workerdecorator.processor.entity.AggregateDocument
import com.octo.workerdecorator.processor.test.CompilationAwareTest
import org.assertj.core.api.Assertions
import org.junit.Test

class KotlinExecutorAggregatorGeneratorTest : CompilationAwareTest() {

    @Test
    fun `it generates the wanted kotlin file`() {
        // Given
        val generator = KotlinExecutorAggregatorGenerator()

        // When
        val result = generator.generate(AggregateDocument.AGGREGATOR, aggregateFixture())

        // Then
        val targetContent = readResource("ExpectedWorkerDecorator.kt")
        Assertions.assertThat(result).isEqualTo(targetContent)
    }

    private fun readResource(file: String) =
        javaClass.classLoader.getResourceAsStream(file).bufferedReader().use { it.readText() }
}
