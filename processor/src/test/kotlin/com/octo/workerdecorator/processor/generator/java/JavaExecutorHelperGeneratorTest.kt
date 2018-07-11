package com.octo.workerdecorator.processor.generator.java

import com.octo.workerdecorator.processor.entity.HelperDocument
import com.octo.workerdecorator.processor.test.CompilationAwareTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class JavaExecutorHelperGeneratorTest : CompilationAwareTest() {

    @Test
    fun `it generates the wanted java file`() {
        // Given
        val generator = JavaExecutorHelperGenerator()

        // When
        val result = generator.generate(HelperDocument.AGGREGATOR, aggregateFixture())

        // Then
        val targetContent = readResource("ExpectedWorkerDecorator.java")
        assertThat(result).isEqualTo(targetContent)
    }
}
