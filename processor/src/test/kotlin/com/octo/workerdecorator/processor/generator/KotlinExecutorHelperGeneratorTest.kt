package com.octo.workerdecorator.processor.generator

import com.octo.workerdecorator.processor.entity.HelperDocument
import com.octo.workerdecorator.processor.test.CompilationAwareTest
import org.assertj.core.api.Assertions
import org.junit.Test

class KotlinExecutorHelperGeneratorTest : CompilationAwareTest() {

    @Test
    fun `it generates the wanted kotlin file`() {
        // Given
        val generator = KotlinExecutorHelperGenerator()

        // When
        val result = generator.generate(HelperDocument.AGGREGATOR, aggregateFixture())

        // Then
        val targetContent = readResource("ExpectedWorkerDecorator.kt")
        Assertions.assertThat(result).isEqualTo(targetContent)
    }

    private fun readResource(file: String) =
        javaClass.classLoader.getResourceAsStream(file).bufferedReader().use { it.readText() }
}
