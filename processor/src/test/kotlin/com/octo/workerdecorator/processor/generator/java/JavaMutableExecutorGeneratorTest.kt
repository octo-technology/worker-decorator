package com.octo.workerdecorator.processor.generator.java

import com.octo.workerdecorator.processor.test.CompilationAwareTest
import org.assertj.core.api.Assertions.assertThat
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
        assertThat(result).isEqualTo(targetContent)
    }
}