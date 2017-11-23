package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Document
import com.octo.workerdecorator.processor.test.fixture.KotlinChildrenInterface
import com.octo.workerdecorator.processor.test.CompilationAwareTest
import com.octo.workerdecorator.processor.test.fixture.KotlinInterface
import com.octo.workerdecorator.processor.test.fixture.JavaInterface
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import javax.lang.model.type.TypeKind.BOOLEAN

class AnalyserTest : CompilationAwareTest() {

    @Test
    fun `analyses a simple kotlin interface`() {
        // Given
        val analyser = Analyser(compilationRule.elements)

        val input = typeElement(KotlinInterface::class)
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

        val input = typeElement(KotlinChildrenInterface::class)

        val methods = listOf(
                methodFixture("daddy", parameterFixture("arg0", String::class)),
                methodFixture("son", parameterFixture("arg0", BOOLEAN)))

        val expected = Document("com.octo.workerdecorator.processor.test.fixture",
                "KotlinChildrenInterfaceDecorated", methods, input.asType(), true)

        // When
        val document = analyser.analyse(input)

        // Then
        assertThat(document).isEqualTo(expected)
    }

    @Test
    fun `analyses a simple java interface`() {
        // Given
        val analyser = Analyser(compilationRule.elements)

        val input = typeElement(JavaInterface::class)
        val expected = simpleJavaInterfaceFixture()

        // When
        val document = analyser.analyse(input)

        // Then
        assertThat(document).isEqualTo(expected)
    }
}