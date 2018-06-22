package com.octo.workerdecorator.processor.generator

import com.octo.workerdecorator.processor.test.CompilationAwareTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class JavaImmutableWeakExecutorGeneratorTest : CompilationAwareTest() {

    @Test
    fun `it generates the wanted java file`() {
        // Given
        val generator = JavaImmutableWeakExecutorGenerator()
        val document = simpleInterfaceFixture()

        // When
        val result = generator.generate(document)

        // Then
        val targetContent = readResource("ExpectedSimpleInterfaceWeakWorkerDecoration.java")
        assertThat(result).isEqualTo(targetContent)
    }

    private fun readResource(file: String) =
        javaClass.classLoader.getResourceAsStream(file).bufferedReader().use { it.readText() }
}