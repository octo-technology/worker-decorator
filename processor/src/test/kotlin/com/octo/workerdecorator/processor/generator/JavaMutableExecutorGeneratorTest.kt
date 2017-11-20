package com.octo.workerdecorator.processor.generator

import com.octo.workerdecorator.processor.test.fixture.CompilationAwareTest
import org.assertj.core.api.Assertions
import org.junit.Test

class JavaMutableExecutorGeneratorTest : CompilationAwareTest() {

    @Test
    fun `it generates the wanted java file`() {
        // Given
        val generator = JavaMutableExecutorGenerator()
        val document = simpleInterfaceFixture()

        // When
        val result = generator.generate(document)

        // Then
        val targetContent = readResource("ExpectedSimpleInterfaceMutableWorkerDecoration.java")
        Assertions.assertThat(result).isEqualTo(targetContent)
    }

    private fun readResource(file: String)
            = javaClass.classLoader.getResourceAsStream(file).bufferedReader().use { it.readText() }
}