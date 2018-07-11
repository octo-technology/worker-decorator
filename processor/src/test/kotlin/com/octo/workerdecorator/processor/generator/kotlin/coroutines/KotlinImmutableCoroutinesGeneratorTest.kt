package com.octo.workerdecorator.processor.generator.kotlin.coroutines

import com.octo.workerdecorator.processor.test.CompilationAwareTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KotlinImmutableCoroutinesGeneratorTest : CompilationAwareTest() {

    @Test
    fun `it generates the wanted kotlin file`() {
        // Given
        val generator = KotlinImmutableCoroutinesGenerator()
        val document = simpleInterfaceFixture()

        // When
        val result = generator.generate(document)

        // Then
        // TODO Write the expected output and replace the loaded file
        val targetContent = readResource("ExpectedSimpleInterfaceWorkerDecoration.kt")
        assertThat(result).isEqualTo(targetContent)
    }
}