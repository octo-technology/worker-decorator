package com.octo.kotlinelements

import com.google.testing.compile.CompilationRule
import com.octo.kotlinelements.fixture.JavaClass
import com.octo.kotlinelements.fixture.KotlinClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test

class TypeElementTest {

    @JvmField
    @Rule
    var compilationRule = CompilationRule()

    @Test
    fun `can tell if TypeElement comes from the Kotlin compiler`() {
        val typeElement = compilationRule.elements.getTypeElement(KotlinClass::class.java.name)

        assertThat(typeElement.isProducedByKotlin()).isTrue()
    }

    @Test
    fun `can tell if TypeElement does not come from the Kotlin compiler`() {
        val typeElement = compilationRule.elements.getTypeElement(JavaClass::class.java.name)

        assertThat(typeElement.isProducedByKotlin()).isFalse()
    }
}