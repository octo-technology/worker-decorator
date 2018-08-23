package com.octo.workerdecorator.processor.generator.kotlin.executor

import com.octo.workerdecorator.processor.test.CompilationAwareTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KotlinMutableWeakExecutorGeneratorTest : CompilationAwareTest() {

    @Test
    fun `it generates the wanted kotlin file`() {
        // Given
        val generator = KotlinMutableWeakExecutorGenerator()
        val document = simpleInterfaceFixture()

        // When
        val result = generator.generate(document)

        // Then
        val targetContent = readResource("ExpectedSimpleInterfaceWeakMutableWorkerDecoration.kt")
        assertThat(result).isEqualTo(targetContent)
    }
}