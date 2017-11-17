package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Document
import com.octo.workerdecorator.processor.test.fixture.ChildrenInterface
import com.octo.workerdecorator.processor.test.fixture.CompilationAwareTest
import com.octo.workerdecorator.processor.test.fixture.SimpleInterface
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import javax.lang.model.type.TypeKind.BOOLEAN

class AnalyserTest : CompilationAwareTest() {

    @Test
    fun `analyses a simple kotlin interface`() {
        // Given
        val analyser = Analyser(compilationRule.elements)

        val input = typeElement(SimpleInterface::class)
        val expected = simpleInterfaceFixture()

        // When
        val document = analyser.analyse(input)

        // Then
        assertThat(document).isEqualTo(expected)
    }

    @Test
    fun `analyses a kotlin interface with inheritance`() {
        // Given
        val analyser = Analyser(compilationRule.elements)

        val input = typeElement(ChildrenInterface::class)

        val methods = listOf(
                methodFixture("daddy", parameterFixture("arg0", String::class)),
                methodFixture("son", parameterFixture("arg0", BOOLEAN)))

        val expected = Document("com.octo.workerdecorator.processor.test.fixture",
                "ChildrenInterfaceDecorated", methods, input.asType())

        // When
        val document = analyser.analyse(input)

        // Then
        assertThat(document).isEqualTo(expected)
    }
}