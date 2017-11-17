package com.octo.workerdecorator.processor.generator

import com.octo.workerdecorator.processor.entity.Document
import com.octo.workerdecorator.processor.test.fixture.CompilationAwareTest
import com.octo.workerdecorator.processor.test.fixture.SimpleInterface
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import javax.lang.model.type.TypeKind.INT

class KotlinImmutableExecutorGeneratorTest : CompilationAwareTest() {

    @Test
    fun `it generate the wanted kotlin file`() {
        // Given
        val typeElement = typeElement(SimpleInterface::class)

        val generator = KotlinImmutableExecutorGenerator()

        val methods = listOf(
                methodFixture("pam"),
                methodFixture("jim",
                        listOf(
                                parameterFixture("arg0", INT),
                                parameterFixture("arg1", String::class))))

        val document = Document("com.octo.workerdecorator.processor.test.fixture",
                "SimpleInterfaceDecorated", methods, typeElement.asType())

        // When
        val result = generator.generate(document)

        // Then
        val targetContent = readResource("ExpectedSimpleInterfaceWorkerDecoration.kt")
        assertThat(result).isEqualTo(targetContent)
    }

    private fun readResource(file: String)
            = javaClass.classLoader.getResourceAsStream(file).bufferedReader().use { it.readText() }
}