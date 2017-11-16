package com.octo.workerdecorator.processor

import com.google.testing.compile.CompilationRule
import com.octo.workerdecorator.processor.entity.Document
import com.octo.workerdecorator.processor.entity.Method
import com.octo.workerdecorator.processor.entity.Parameter
import com.octo.workerdecorator.processor.test.fixture.ChildrenInterface
import com.octo.workerdecorator.processor.test.fixture.SimpleInterface
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import javax.lang.model.type.TypeKind
import kotlin.reflect.KClass

class AnalyserTest {

    @JvmField
    @Rule
    var compilationRule = CompilationRule()

    @Test
    fun `analyses a simple kotlin interface`() {
        // Given
        val analyser = Analyser(compilationRule.elements)

        val input = typeElement(SimpleInterface::class)

        val methods = listOf(
                Method("pam", emptyList()),
                Method("jim",
                        listOf(
                                Parameter("arg0",
                                        typeElement(TypeKind.INT)),
                                Parameter("arg1",
                                        typeElement(String::class).asType())))
        )
        val expected = Document("SimpleInterfaceDecorated", methods)

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
                Method("daddy",
                        listOf(Parameter("arg0",
                                typeElement(String::class).asType()))),
                Method("son",
                        listOf(Parameter("arg0",
                                typeElement(TypeKind.BOOLEAN))))
        )
        val expected = Document("ChildrenInterfaceDecorated", methods)

        // When
        val document = analyser.analyse(input)

        // Then
        assertThat(document).isEqualTo(expected)
    }

    private fun <T : Any> typeElement(`class`: KClass<T>)
            = compilationRule.elements.getTypeElement(`class`.java.name)

    private fun typeElement(kind: TypeKind)
            = compilationRule.types.getPrimitiveType(kind)
}