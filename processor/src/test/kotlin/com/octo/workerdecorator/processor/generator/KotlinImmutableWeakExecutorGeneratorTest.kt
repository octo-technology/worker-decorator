package com.octo.workerdecorator.processor.generator

import com.octo.workerdecorator.processor.test.CompilationAwareTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KotlinImmutableWeakExecutorGeneratorTest : CompilationAwareTest() {

    @Test
    fun `it generates the wanted kotlin file`() {
        // Given
        val generator = KotlinImmutableWeakExecutorGenerator()
        val document = simpleInterfaceFixture()

        // When
        val result = generator.generate(document)

        // Then
        val targetContent = readResource("ExpectedSimpleInterfaceWeakWorkerDecoration.kt")
        assertThat(result).isEqualTo(targetContent)
    }

    private fun readResource(file: String) =
        javaClass.classLoader.getResourceAsStream(file).bufferedReader().use { it.readText() }
}