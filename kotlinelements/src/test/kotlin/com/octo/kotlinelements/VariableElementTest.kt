package com.octo.kotlinelements

import com.google.testing.compile.CompilationRule
import com.octo.kotlinelements.fixture.JavaClass
import com.octo.kotlinelements.fixture.KotlinClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import javax.lang.model.element.ElementKind
import javax.lang.model.element.VariableElement
import kotlin.reflect.KClass

class VariableElementTest {

    @JvmField
    @Rule
    var compilationRule = CompilationRule()

    /*
     * Java input
     */

    @Test
    fun `can check a java int variable element`() {
        val element = getVariableElement("anInt", JavaClass::class)
        assertThat(element.isPrimitive()).isTrue()
        assertThat(element.isOptional()).isFalse()
    }

    @Test
    fun `can check a java boxed integer variable element`() {
        val element = getVariableElement("aBoxedInt", JavaClass::class)
        assertThat(element.isPrimitive()).isFalse()
        assertThat(element.isOptional()).isTrue()
    }

    @Test
    fun `can check a java object variable element`() {
        val element = getVariableElement("aString", JavaClass::class)
        assertThat(element.isPrimitive()).isFalse()
        assertThat(element.isOptional()).isTrue()
    }

    @Test
    fun `can check a @NotNull java object variable element`() {
        val element = getVariableElement("aNotNullString", JavaClass::class)
        assertThat(element.isPrimitive()).isFalse()
        assertThat(element.isOptional()).isFalse()
    }

    /*
     * Kotlin input
     */

    @Test
    fun `can check a kotlin Int variable element`() {
        val element = getVariableElement("anInt", KotlinClass::class)
        assertThat(element.isPrimitive()).isTrue()
        assertThat(element.isOptional()).isFalse()
    }

    @Test
    fun `can check a kotlin Int? variable element`() {
        // A kotlin `Int` is compiles to a java `int`
        // A kotlin `Int?` is compiled to java `Integer`
        val element = getVariableElement("anOptionalInt", KotlinClass::class)
        assertThat(element.isPrimitive()).isFalse()
        assertThat(element.isOptional()).isTrue()
    }

    @Test
    fun `can check a kotlin String variable element`() {
        val element = getVariableElement("aString", KotlinClass::class)
        assertThat(element.isPrimitive()).isFalse()
        assertThat(element.isOptional()).isFalse()
    }

    @Test
    fun `can check a kotlin String? object variable element`() {
        val element = getVariableElement("anOptionalString", KotlinClass::class)
        assertThat(element.isPrimitive()).isFalse()
        assertThat(element.isOptional()).isTrue()
    }

    private fun <T : Any> getVariableElement(name: String, `class`: KClass<T>): VariableElement {
        return compilationRule.elements.getTypeElement(`class`.java.name)
                .enclosedElements
                .filter { it.kind == ElementKind.FIELD }
                .map { it as VariableElement }
                .find { it.simpleName.toString() == name }!!
    }
}
