package com.octo.workerdecorator.processor

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.octo.workerdecorator.annotation.Decorate
import com.octo.workerdecorator.processor.entity.AggregateDocument
import com.octo.workerdecorator.processor.entity.DecorationDocument
import com.octo.workerdecorator.processor.entity.Mutability.MUTABLE
import com.octo.workerdecorator.processor.test.CompilationAwareTest
import com.octo.workerdecorator.processor.test.fixture.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import javax.lang.model.type.TypeKind.BOOLEAN
import javax.lang.model.type.TypeKind.INT

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
            methodFixture("son", parameterFixture("arg0", BOOLEAN))
        )

        val expected = DecorationDocument(
            "com.octo.workerdecorator.processor.test.fixture",
            "KotlinChildrenInterfaceDecorated",
            methods,
            input.asType(),
            true
        )

        // When
        val document = analyser.analyse(input)

        // Then
        assertThat(document).isEqualTo(expected)
    }

    @Test
    fun `analyses a simple kotlin interface with primitive and boxed type`() {
        // Given
        val analyser = Analyser(compilationRule.elements)

        val input = typeElement(KotlinInterfaceWithPrimitives::class)

        val parameters = listOf(
            parameterFixture("arg0", java.lang.Boolean::class, optional = true),
            parameterFixture("arg1", INT)
        )
        val methods = listOf(methodFixture("a", parameters))

        val expected = DecorationDocument(
            "com.octo.workerdecorator.processor.test.fixture",
            "KotlinInterfaceWithPrimitivesDecorated",
            methods,
            input.asType(),
            true
        )

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

    @Test
    fun `analyses a simple java interface with primitive and boxed type`() {
        // Given
        val analyser = Analyser(compilationRule.elements)

        val input = typeElement(JavaInterfaceWithPrimitives::class)

        val parameters = listOf(
            parameterFixture("arg0", java.lang.Boolean::class, optional = true),
            parameterFixture("arg1", INT)
        )
        val methods = listOf(methodFixture("a", parameters))

        val expected = DecorationDocument(
            "com.octo.workerdecorator.processor.test.fixture",
            "JavaInterfaceWithPrimitivesDecorated",
            methods,
            input.asType(),
            false
        )

        // When
        val document = analyser.analyse(input)

        // Then
        assertThat(document).isEqualTo(expected)
    }

    @Test
    fun `aggregates elements`() {
        // Given
        val analyser = Analyser(compilationRule.elements)

        val input = typeElement(KotlinInterface::class)
        val expected = simpleInterfaceFixture()

        val annotation = mock<Decorate>()
        given(annotation.mutable).willReturn(true)

        // When
        val documents = analyser.analyse(listOf(Pair(input, annotation)))

        // Then
        assertThat(documents).isEqualTo(
            listOf(
                AggregateDocument(
                    "com.octo.workerdecorator.processor.test.fixture",
                    "KotlinInterfaceDecorated",
                    input.asType(),
                    MUTABLE
                )
            )
        )
    }
}