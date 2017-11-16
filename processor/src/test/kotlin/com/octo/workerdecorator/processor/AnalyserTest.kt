package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Document
import com.octo.workerdecorator.processor.test.fixture.ChildrenInterface
import com.octo.workerdecorator.processor.test.fixture.CompilationAwareTest
import com.octo.workerdecorator.processor.test.fixture.SimpleInterface
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import javax.lang.model.type.TypeKind.BOOLEAN
import javax.lang.model.type.TypeKind.INT

class AnalyserTest : CompilationAwareTest() {

    @Test
    fun `analyses a simple kotlin interface`() {
        // Given
        val analyser = Analyser(compilationRule.elements)

        val input = typeElement(SimpleInterface::class)

        val methods = listOf(
                methodFixture("pam", input),
                methodFixture("jim",
                        listOf(
                                parametedFixture("arg0", INT),
                                parametedFixture("arg1", String::class)),
                        input))

        val expected = Document("com.octo.workerdecorator.processor.test.fixture",
                "SimpleInterfaceDecorated", methods, input.asType())

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
                methodFixture("daddy", parametedFixture("arg0", String::class), input),
                methodFixture("son", parametedFixture("arg0", BOOLEAN), input))

        val expected = Document("com.octo.workerdecorator.processor.test.fixture",
                "ChildrenInterfaceDecorated", methods, input.asType())

        // When
        val document = analyser.analyse(input)

        // Then
        assertThat(document).isEqualTo(expected)
    }
}